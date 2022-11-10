package com.jia.home.controller;


import com.jia.home.utils.RespBean;
import com.jia.home.model.UploadFile;
import com.jia.home.utils.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private UploadFileUtils uploadFileUtils;

    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public RespBean uploadFile(UploadFile uploadFile){
        System.out.println(uploadFile);
        MultipartFile files = uploadFile.getFiles();
        System.out.println(files);
        return uploadFileUtils.getWebPath(files,uploadFile.getPath(),uploadFile.getAddFolder(),uploadFile.getKeepName());
    }
}
