package com.jia.home.controller;


import com.jia.home.model.Bed;
import com.jia.home.service.BedService;
import com.jia.home.utils.RespBean;
import com.jia.home.utils.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiawei
 * @since 2022-04-27
 */
@RestController
@RequestMapping("/bed")
public class BedController {
    @Autowired
    private BedService bedService;

    @GetMapping("/getAll")
    public RespBean getAll(){
        List<Bed> list = bedService.list();
        return RespBean.ok(list);
    }

    @GetMapping("/getByPage")
    public RespPageBean getByPage(@RequestParam Map<String,Object> params){
        return bedService.getByPage(params);
    }

    @PostMapping("/UpdateStatus")
    public RespBean updateStatus(@RequestBody Integer integer){
        return bedService.updateStatus(integer);
    }

    @PostMapping("/UpdateBed")
    public RespBean updateStatus(@RequestBody Bed bed){
        return bedService.updateBed(bed);
    }
}
