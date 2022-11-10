package com.jia.home.controller;


import com.jia.home.model.Menu;
import com.jia.home.model.User;
import com.jia.home.model.UserMenu;
import com.jia.home.service.UserMenuService;
import com.jia.home.utils.PageUtils;
import com.jia.home.utils.RespBean;
import com.jia.home.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiawei
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserMenuService userMenuService;
    @PostMapping("/getByUser")
    public RespBean getByUser(@RequestBody User user){
        return userMenuService.getPageById(user.getId());
    }
    @GetMapping("/getAll")
    public RespBean getAll(){
        List<Menu> menus = menuService.listToTree();
        return RespBean.ok(menus);
    }
    @RequestMapping("/getAllByPage")
    public RespBean getAllByPage(@RequestParam Map<String,Object> params){
        PageUtils page =  menuService.getAllByPage(params);
        return RespBean.ok(page);
    }
    @RequestMapping("/update")
    public RespBean update(@RequestBody Menu menu){
        boolean b = menuService.updateById(menu);
        if (b){
            return RespBean.ok();
        }else {
            return RespBean.error("插入失败");
        }
    }
    @DeleteMapping("/delete/{id}")
    public RespBean delete(@PathVariable("id") Integer id){
        boolean b = menuService.removeById(id);
        if (b){
            return RespBean.ok();
        }else {
            return RespBean.error("删除失败");
        }
    }

    @PostMapping("/add")
    public RespBean add(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

}
