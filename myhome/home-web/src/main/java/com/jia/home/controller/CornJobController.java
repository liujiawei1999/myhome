package com.jia.home.controller;

import com.jia.home.model.CornJob;
import com.jia.home.service.CornJobService;
import com.jia.home.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class CornJobController {
    @Autowired
    private CornJobService cornJobService;

    @GetMapping("/list")
    public RespBean getAllJob(){
        List<CornJob> list = cornJobService.list();
        return RespBean.ok(list);
    }

    @DeleteMapping("/delJob/{id}")
    public RespBean delJob(@PathVariable(value = "id") Integer id){
       return cornJobService.delJobById(id);
    }

    @PostMapping("/add")
    public RespBean addJob(@RequestBody CornJob cornJob){
        return cornJobService.addJob(cornJob);
    }

}
