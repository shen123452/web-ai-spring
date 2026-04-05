package com.hhh.controller;

import com.hhh.pojo.Result;
import com.hhh.utils.AliyunOSSOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UploadController {
    private final AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result<String> upload(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }
        log.info("Upload file, name: {}, age: {}, originalFilename: {}", name, age, file.getOriginalFilename());
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("Upload file success, url: {}", url);
        return Result.success(url);
    }
}
