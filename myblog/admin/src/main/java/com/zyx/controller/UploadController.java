package com.zyx.controller;

import com.zyx.domin.ResponseResult;
import com.zyx.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;


    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
