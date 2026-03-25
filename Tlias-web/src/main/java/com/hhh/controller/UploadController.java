package com.hhh.controller;

import com.hhh.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
public class UploadController {
    @PostMapping("/upload")
    public Result upload(String name ,Integer age,MultipartFile file) throws IOException {
        log.info("接收参数：{},{},{}",name,age,file);
        //接收文件名
        String originalFilename = file.getOriginalFilename();
        //创建新的文件名--防止文件被覆盖，可以使用uuid
        String  extension= originalFilename.substring(originalFilename.lastIndexOf("."));
        
        //保存文件
        file.transferTo(new File("D:/images/"+originalFilename));
        return Result.success();
    }


}
