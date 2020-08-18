package com.example.lixc.vo.back;

import com.example.lixc.entity.SysMessage;
import com.example.lixc.entity.SysWork;
import com.example.lixc.entity.SysWorkDict;
import com.example.lixc.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/1 22:28
 */
@Data
public class MessageBack extends SysMessage implements Serializable {
    private static final long serialVersionUID = -7911491702950811545L;

    //添加人的昵称
    private String nickName;

}
