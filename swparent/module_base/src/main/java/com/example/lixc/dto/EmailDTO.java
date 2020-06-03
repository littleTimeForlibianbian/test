package com.example.lixc.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class EmailDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 账号类型
     */
    private String[] account;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 主题
     */
    private String suject;

    /**
     * 是否是html方式发送
     */
    private Boolean html;

    public EmailDTO(String[] account, String content, String suject, Boolean html) {
        super();
        this.account = account;
        this.content = content;
        this.suject = suject;
        this.html = html;
    }

    public EmailDTO() {
        super();
        // TODO Auto-generated constructor stub
    }


    public EmailDTO(String[] account, String content, Boolean html) {
        super();
        this.account = account;
        this.content = content;
        this.html = html;
    }
}
