package com.example.lixc.vo.back;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author Wilson
 * @Description
 * @createTime 2020/7/15 10:35
 */
@Data
public class UserLoginBack {

    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "count")
    private Integer count;
}
