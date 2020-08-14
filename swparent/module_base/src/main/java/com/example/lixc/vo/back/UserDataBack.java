package com.example.lixc.vo.back;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("userData")
@Data
public class UserDataBack implements Serializable {
    private static final long serialVersionUID = -1256447508311472703L;
    private String create_time;
    private int count_user_add;
    private int count_user_active;
    private String location;
    private int count_user_area;
}
