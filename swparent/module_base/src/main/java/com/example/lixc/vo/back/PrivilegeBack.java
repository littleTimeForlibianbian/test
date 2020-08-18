package com.example.lixc.vo.back;

import com.example.lixc.entity.Role;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @author lixc
 * @Description 权限查询返回类
 * @createTime 2020/6/14 22:38
 */
@Data
@ToString
public class PrivilegeBack implements Serializable {
    private static final long serialVersionUID = -2835090875831348875L;
    @Id
    private Integer id;
    private String name;
    private String url;
    private String tag;
    private Integer type;
    private Integer parent;
    /**
     * 存储所属角色  一个权限可能属于多个角色
     */
    private List<Role> roles;
}
