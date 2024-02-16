package com.zyx.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class UploadUtils {

    //阿里域名
    public static final String UPLOAD_PATH = "https://blogstastic-zyx.oss-cn-hangzhou.aliyuncs.com/";

    public static String uploadImage(MultipartFile file)
    {
        //生成文件名 防止冲突
        String originalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //地域节点
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI5t5vYj7fJE2QkeXy7gMX";
        String accessKeySecret = "BxNeuI1F9T7ja9qAIlOT3a8YZjQVwu";
        //oss客户端对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //上传文件流
        try {
            ossClient.putObject(
                    "blogstastic-zyx", // 仓库名
                    "images/" + fileName, // 文件名
                    file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ossClient.shutdown();
        //返回文件路径
        return UPLOAD_PATH + "images/" + fileName;
    }




}
