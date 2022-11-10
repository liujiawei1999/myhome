package com.jia.home.controller;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.home.utils.*;
import com.jia.home.model.User;
import com.jia.home.service.UserService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @GetMapping("/list")
    public RespBean getUsers(){
        List<User> list = userService.list();
        return RespBean.ok(list);
    }
    @PutMapping("/update")
    public RespBean updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @DeleteMapping("/deleted")
    public RespBean deleted(@RequestParam Integer id){
        userService.deleted(id);
        return RespBean.ok("删除成功");
    }

    @GetMapping("/listByPage")
    public RespBean getUsersPage(@RequestParam Map<String,Object> params){
        PageUtils page = userService.getByPage(params);
        return RespBean.ok(page);
    }

    @PutMapping("/{id}/changeUserStatus/{status}")
    public RespBean changeUserStatus(@PathVariable(value = "id") Integer id,@PathVariable(value = "status")Integer status){
       return userService.changeUserStatus(id,status);
    }

    @PostMapping("/add")
    public RespBean addUser(@RequestBody User user){
        String userName = user.getUserName();
        String userPhone = user.getUserPhone();
        String userEmail = user.getUserEmail();
        String userPassword = user.getUserPassword();
        String smsCode = user.getSmsCode();
        if (userPhone==null){
            return RespBean.error("手机号不能为空");
        }
        if (JWStringUtils.isEmpty(userName)){
            userName = userPhone;
        }
        if (JWStringUtils.isEmpty(userPassword)){
            userPassword = RandomUtil.randomString(8);
        }
        User userHad1= userService.getUserByName(userName);
        if (userHad1!=null){
            return RespBean.error("用户名已注册！");
        }
        User userHad2 = userService.getUserByPhone(userPhone);
        if (userHad2!=null){
            return RespBean.error("手机号已注册！");
        }

        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        String hexPasswd = aes.encryptHex(userPassword);
        user.setUserPassword(hexPasswd);
        user.setUserSalt(key);
        //同时创建用户对应角色
        userService.insertUserAndRole(user);
        if (JWStringUtils.isNotEmpty(userEmail)){
            try {
                //TODO rabbit传递第三方服务发送邮件
                String uuid = UUIDUtils.uuid();
                Message build = MessageBuilder
                        .withBody(objectMapper.writeValueAsBytes(user))
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                        .build();
                rabbitTemplate.convertAndSend(MailConstants.RABBIT_EXCHANGE_NAME
                        ,MailConstants.RABBIT_ROUTING_KEY_NAME
                        ,build
                        ,new CorrelationData(uuid));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return RespBean.ok();
    }

}
