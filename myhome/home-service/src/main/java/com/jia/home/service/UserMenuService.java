package com.jia.home.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.home.mapper.UserMenuMapper;
import com.jia.home.model.Menu;
import com.jia.home.model.UserMenu;
import com.jia.home.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserMenuService extends ServiceImpl<UserMenuMapper, UserMenu> {

    @Autowired
    private UserMenuMapper userMenuMapper;

    public RespBean getPageById(Integer id) {
        List<Menu> userMenu =  userMenuMapper.getMenuById(id);
        List<Menu> collect = userMenu.stream().filter(menu -> {
            return menu.getMenuParentId() == 0;
        }).peek(e -> {
            e.setChildren(getChildrenMenu(e, userMenu));
        }).sorted((s1, s2) -> {
            return (s1.getMenuSort() == null ? 0 : s1.getMenuSort()) - (s2.getMenuSort() == null ? 0 : s2.getMenuSort());
        }).collect(Collectors.toList());
        if (userMenu!=null && !userMenu.isEmpty()){
            return RespBean.ok(collect);
        }
        return RespBean.error("未取到数据！");
    }
    private List<Menu> getChildrenMenu(Menu e, List<Menu> menus) {
        List<Menu> collect = menus.stream().filter(menu -> {
            return Objects.equals(menu.getMenuParentId(), e.getMenuId());
        }).collect(Collectors.toList());
        if(collect.isEmpty()){
            return null;
        }
        return collect;
    }

    public RespBean getListById(Integer id) {
        List<Integer> collect = userMenuMapper.getMenuById(id).stream().filter(e -> {
            return e.getMenuParentId() != 0 || e.getMenuType()==1;
        }).map(Menu::getMenuId).collect(Collectors.toList());
        if (collect!=null && !collect.isEmpty()){
            return RespBean.ok(collect);
        }
        return RespBean.error("未取到数据！");
    }
}
