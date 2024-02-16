package com.zyx.service.impl;

import com.zyx.domin.ResponseResult;
import com.zyx.enums.AppHttpCodeEnum;
import com.zyx.service.UploadService;
import com.zyx.utils.UploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型
        String contentType = img.getContentType();
        //只传图片
        if (!contentType.contains("image")) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"文件类型错误");
        }
        return ResponseResult.okResult(UploadUtils.uploadImage(img));
    }
}
