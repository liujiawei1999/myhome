package com.jia.home.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class User implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String userPassword;
    private String userPhone;
    private String userEmail;
    private Integer userStatus;
    private Integer userLoginType;
    private String userAvatarUrl;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(exist = false)
    private String SmsCode;
    private byte[] userSalt;
}
