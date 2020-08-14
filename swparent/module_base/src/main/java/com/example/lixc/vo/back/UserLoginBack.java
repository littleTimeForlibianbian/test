package com.example.lixc.vo.back;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author Wilson
 * @Description
 * @createTime 2020/7/15 10:35
 */
@Data
public class UserLoginBack implements Serializable {

    private static final long serialVersionUID = 5265136022208235367L;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "count")
    private Integer count;
}
