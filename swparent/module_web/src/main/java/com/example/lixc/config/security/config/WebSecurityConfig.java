package com.example.lixc.config.security.config;

import com.example.lixc.config.security.filter.TokenFilter;
import com.example.lixc.mapper.PrivilegeMapper;
import com.example.lixc.vo.back.PrivilegeBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * @author lixc
 * @Description spring security 管理类
 * @createTime 2020/6/14 16:58
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;
    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/public/**")
//                .antMatchers("/*.html")
//                .antMatchers("/favicon.ico")
//                .antMatchers("/**/*.html")
//                .antMatchers("/**/*.css")
//                .antMatchers("/**/*.json")
//                .antMatchers("/**/*.js")
//                .antMatchers("/**/*.png")
//                .antMatchers("/**/*.jpg")
//                .antMatchers("/**/*.jpeg")
                //
                //后台路径匹配
//                .antMatchers("/**/**/*.html")
//                .antMatchers("/**/**/*.css")
//                .antMatchers("/**/**/*.json")
//                .antMatchers("/**/**/*.js")
//                .antMatchers("/**/**/*.png")
//                .antMatchers("/**/**/*.jpg")
//                .antMatchers("/**/**/*.jpeg");
                .antMatchers("/front/**.*")
                .antMatchers("/admin/**.*");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    //    @Override11
    protected void configure11(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()//禁用csrf
                //针对于此处请求的路径  匹配的采用下面的规则（withObjectPostProcessor）进行权限校验
                .antMatcher("/aa/**").authorizeRequests()
//                .antMatchers("/admin/**","/web/**").authenticated()
//                .antMatchers("/security/user/**").hasRole("ADMIN") //需要ADMIN角色才可以访问
//                .antMatchers("/connect").hasIpAddress("127.0.0.1") //只有ip[127.0.0.1]可以访问'/connect'接口
                //增加路径匹配
//                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(mySecurityMetadataSource);
                        object.setAccessDecisionManager(myAccessDecisionManager);
                        return object;
                    }
                })
//                .antMatchers(HttpMethod.DELETE, "/public1/**").hasRole("ADMIN")
                .anyRequest().permitAll()//其余放行
                .and()
//                .rememberMew()
//                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
//                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                .accessDeniedHandler(myAccessDeniedHandler)
                .and()
                .rememberMe()
                .and()
                .formLogin().loginPage("/Loginpage.html").permitAll()
                .and()
                .logout().permitAll();
    }

    @Bean
    public TokenFilter tokenFilterBean() throws Exception {
        return new TokenFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置允许springboot加载静态资源
//        http.headers().contentTypeOptions().disable();
        // 添加JWT filter
        http.addFilterBefore(tokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http.authorizeRequests();
        List<PrivilegeBack> allPrivilege = privilegeMapper.findAllPrivilege();
        for (PrivilegeBack back : allPrivilege) {
            expressionInterceptUrlRegistry.antMatchers(back.getUrl()).hasAnyAuthority(back.getTag());
        }
        expressionInterceptUrlRegistry
                //对于public开头的数据 都不进行鉴权，
                .antMatchers("/public/**").permitAll()
                //除上面之外的所有请求  都要进行鉴权认证
                .anyRequest().authenticated()
//                .antMatchers("/**").fullyAuthenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                .accessDeniedHandler(myAccessDeniedHandler)
                .and()
                .rememberMe()
                .and()
                .formLogin().loginPage("/Loginpage.html").permitAll()
                .and()
                .logout()
                // 退出登录的url
//                .logoutUrl("/public/user/logout")
                .logoutSuccessUrl("/public/user/logout");
        // 禁用缓存
        http.headers().cacheControl();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


}
