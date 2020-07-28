package com.example.lixc.vo.back;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("userData")
@Data
public class UserDataBack {
    private String create_time;
    private int count_user_add;
    private int count_user_active;
    private String location;
    private int count_user_area;
}
