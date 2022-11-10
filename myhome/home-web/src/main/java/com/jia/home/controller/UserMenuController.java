package com.jia.home.controller;


import com.jia.home.model.User;
import com.jia.home.service.UserMenuService;
import com.jia.home.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author jiawei
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/userPage")
public class UserMenuController {

    @Autowired
    private UserMenuService userMenuService;

    @GetMapping("/getPage/{id}")
    public RespBean getByUserId(@PathVariable(value = "id") Integer id){
        return userMenuService.getPageById(id);
    }
    @PostMapping("/getListByUser")
    public RespBean getByUserId(@RequestBody User user){
        return userMenuService.getListById(user.getId());
    }
}
