package com.example.lixc.config.token;

import com.example.lixc.entity.User;
import com.example.lixc.vo.back.UserBack;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

/**
 * @className: TokenUtils
 * @description: token工具类，具有生成token和验证token的功能
 * @Author: Wilson
 * @createTime 2020/8/4 23:41
 */
@Component
public class TokenUtils implements Serializable {
    private static final long serialVersionUID = 1404233008638686057L;

    /**
     * Token 有效时长
     */
    private static final Long EXPIRATION = 604800L;

    public String createToken(String nickName) {
        try {
            // Token 的过期时间
            Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
            // 生成 Token
            String token = Jwts.builder()
                    // 设置 Token 签发者 可选
                    .setIssuer("SpringBoot")
                    // 根据用户名设置 Token 的接受者
                    .setAudience(nickName)
                    // 设置过期时间
                    .setExpiration(expirationDate)
                    // 设置 Token 生成时间 可选
                    .setIssuedAt(new Date())
//                    .claim("role", sysUser.getUserRole())
                    // 设置加密密钥和加密算法，注意要用私钥加密且保证私钥不泄露
                    .signWith(SignatureAlgorithm.RS256, RSAUtils.getKeyPair().getPrivate())
                    .compact();
            return String.format("Bearer %s", token);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证 Token ，并获取到用户名和用户权限信息
     *
     * @param token Token 字符串
     * @return sysUser 用户信息
     */
    public String validationToken(String token) {
        try {
            // 解密 Token，获取 Claims 主体
            Claims claims = Jwts.parser()
                    // 设置公钥解密，因为私钥是保密的，因此 Token 只能是自己生成的，如此来验证 Token
                    .setSigningKey(RSAUtils.getKeyPair().getPublic())
//                    .setSigningKey(RSAUtils.getKeyPair().getPublic())
                    .parseClaimsJws(token.split(" ")[1]).getBody();
            assert claims != null;
            // 验证 Token 有没有过期 过期时间
            Date expiration = claims.getExpiration();
            // 判断是否过期 过期时间要在当前日期之后
            if (!expiration.after(new Date())) {
                return null;
            }
            return claims.getAudience();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = validationToken(token);
        return (
                username.equals(userDetails.getUsername()));
    }

    public static void main(String[] args) {
        TokenUtils utils = new TokenUtils();
        String token = utils.createToken("admin");
        System.out.println("token:" + token);
        String nickName = utils.validationToken(token);
        System.out.println("nickName:" + nickName);
    }

}
