package com.example.lixc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 21:23
 */
@Data
@Table(name = "u_focus")
public class UFocus {
    @Id
    private Integer id;
    private Integer userId;
    private Integer authorId;
    private Date createTime;
}
