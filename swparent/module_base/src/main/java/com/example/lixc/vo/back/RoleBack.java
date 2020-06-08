package com.example.lixc.vo.back;

import com.example.lixc.entity.Privilege;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author lixc
 * @Description 角色返回类
 * @createTime 2020/6/7 22:54
 */
@Data
@ToString
public class RoleBack {
    private Integer id;

    private String name;

    private String roleDescription;

    private String enable;

    private Date createTime;

    private List<Privilege> privilegeList;
}
