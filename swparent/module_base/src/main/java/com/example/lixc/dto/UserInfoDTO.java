package com.example.lixc.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author Wilson
 * @Description
 * @createTime 2020/7/16 17:14
 */
@Data
@ToString
public class UserInfoDTO {
    private int userId;
    private String userName;
    private String headImage;
    private String painter;
    private String uHistory;
    private String website;
}
