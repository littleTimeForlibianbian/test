package com.example.lixc.vo.back;

import com.example.lixc.entity.Privilege;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lixc
 * @Description 角色返回类
 * @createTime 2020/6/7 22:54
 */
@Data
@ToString
public class RoleBack implements Serializable {
    private static final long serialVersionUID = -1554941716661018400L;
    private Integer id;

    private String name;

    private String roleDescription;

    private String enable;

    private Date createTime;

    private List<Privilege> privilegeList;
}
