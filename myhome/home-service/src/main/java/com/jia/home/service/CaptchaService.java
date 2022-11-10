package com.jia.home.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.jia.home.utils.JWException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public CircleCaptcha getCaptcha(String uuid, HttpServletRequest request) {
        if (uuid==null){
            throw new JWException("uuid不能为空");
        }
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(100, 50, 4,2);
        String code = circleCaptcha.getCode();
        redisTemplate.opsForValue().set(uuid,code,60, TimeUnit.SECONDS);
        request.getSession(true).setAttribute("captcha_code",code);
        return circleCaptcha;
    }

    public boolean validate(String uuid, String captcha, HttpServletRequest request) {
        String code = redisTemplate.opsForValue().getAndDelete(uuid);
        if (code==null){
            return false;
        }
        if (code.equalsIgnoreCase(captcha)){
            return true;
        }
        return false;
    }
}
