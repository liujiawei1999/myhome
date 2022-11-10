package com.jia.home.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.home.mapper.UserMapper;
import com.jia.home.utils.PageUtils;
import com.jia.home.model.User;
import com.jia.home.utils.Query;
import com.jia.home.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    UserMapper userMapper;

    public User queryByName(String userName) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_name", userName));
    }

    public void deleted(Integer id) {
        userMapper.deleteById(id);
    }

    public PageUtils getByPage(Map<String, Object> params) {
        String query =(String) params.get("query");
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (StringUtils.hasLength(query)){
            userQueryWrapper.like("user_name",query);
        }
        IPage<User> userIPage = this.page(new Query<User>().getPage(params), userQueryWrapper);
        return new PageUtils(userIPage);
    }

    public RespBean addUser(User user) {
        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("user_name", user.getUserName()));
        if (users==null || users.isEmpty()){
//            SecretKeyFactory.getInstance()
            userMapper.insert(user);
            return RespBean.ok();
        }else {
            return RespBean.error("用户名重复");
        }

    }

    public RespBean updateUser(User user) {
        User oldUser = userMapper.selectById(user.getId());
        if (user.getUserName()!=null && !Objects.equals(user.getUserName(), oldUser.getUserName())){
            return RespBean.error("暂不支持更改用户名");
        }
        userMapper.updateById(user);
        return RespBean.ok();
    }

    public User getUserByName(String userName) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_name",userName));
    }

    public User getUserByPhone(String userPhone) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_phone",userPhone));
    }

    public void insertUserAndRole(User user) {
        userMapper.insert(user);
    }

    public RespBean changeUserStatus(Integer id, Integer status) {
        User user = new User();
        user.setUserStatus(status);
        user.setId(id);
        int i = userMapper.updateById(user);
        System.out.println(i);
        return RespBean.ok();
    }
}
