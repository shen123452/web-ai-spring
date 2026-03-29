package com.hhh.controller;

import com.hhh.pojo.Result;
import com.hhh.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {
    /*
    * 本地文件存储*/
   /* @PostMapping("/upload")
    public Result upload(String name ,Integer age,MultipartFile file) throws IOException {
        log.info("接收参数：{},{},{}",name,age,file);
        //接收文件名
        String originalFilename = file.getOriginalFilename();
        //创建新的文件名--防止文件被覆盖，可以使用uuid
        String  extension= originalFilename.substring(originalFilename.lastIndexOf("."));
        
        //保存文件
        file.transferTo(new File("D:/images/"+originalFilename));
        return Result.success();
    }*/


    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
public Result upload(MultipartFile file) throws Exception {
    log.info("文件上传成功:{}"+file.getOriginalFilename());
    //将文件交给OSS存储管理
   String url =  aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
   log.info("url:{}",url);
   return Result.success(url);
}


}
