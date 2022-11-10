package com.jia.home.controller;


import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.jia.home.utils.RespBean;
import com.jia.home.model.User;
import com.jia.home.service.CaptchaService;
import com.jia.home.service.UserService;
import com.jia.home.utils.UUIDUtils;
import com.jia.home.vo.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request,HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        CircleCaptcha image = captchaService.getCaptcha(uuid,request);
        ServletOutputStream out = response.getOutputStream();
        image.write(out);
        try{
            if (out!=null){
                out.close();
            }
        }catch (IOException e){
          e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public RespBean validate(HttpServletRequest request,@RequestBody LoginParam loginParam){
        boolean isLegal = captchaService.validate(loginParam.getUuid(),loginParam.getCaptcha(),request);
        if (!isLegal){
            return RespBean.error("验证码错误");
        }
        User user = userService.queryByName(loginParam.getUserName());
//        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, user.getUserSalt());
//        String hexPasswd = aes.encryptHex(loginParam.getPassword());
//        if (!user.getUserPassword().equals(hexPasswd)){
//            return RespBean.error("账号或密码错误");
//        }
//        if (user.getUserStatus() == 0){
//            return RespBean.error("账号被锁定");
//        }
        Map<String, Object> res = new HashMap<>();
        String uuid = UUIDUtils.uuid();
        res.put("uuid",uuid);
        System.out.println(uuid);
        redisTemplate.opsForValue().set(uuid,uuid,120, TimeUnit.MINUTES);
        res.put("userInfo",user);
        return RespBean.ok(res);
    }

}
