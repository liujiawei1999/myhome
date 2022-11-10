package com.jia.home.vo;


import lombok.Data;

@Data
public class LoginParam {
    private String userName;
    private String password;
    private String uuid;
    private String captcha;
}
