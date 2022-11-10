package com.jia.home.controller;

import com.jia.home.service.DictionaryService;
import com.jia.home.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dic")
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @GetMapping("getBuilding")
    public RespBean getAllBuilding(){
        return dictionaryService.getBedBuilding();

    }
    @GetMapping("getCommunity")
    public RespBean getAllCommunity(){
        return dictionaryService.getCommunity();
    }

}
