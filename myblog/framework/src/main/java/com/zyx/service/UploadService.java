package com.zyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyx.domin.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService{
    ResponseResult uploadImg(MultipartFile img);
}
