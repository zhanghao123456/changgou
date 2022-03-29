package com.changgou.controller;

import com.changgou.file.FastFDSFile;
import com.changgou.util.FastDFSUtil;
import entity.Result;
import entity.StatusCode;
import org.csource.common.MyException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class FileUploadController {

    @PostMapping
    public Result upload(@RequestParam("file") MultipartFile file) throws Exception {

        FastFDSFile fastFDSFile = new FastFDSFile(
                file.getOriginalFilename(),
                file.getBytes(),
                StringUtils.getFilenameExtension(file.getOriginalFilename())
        );
        //FastDFSUtil工具类将文件传入到FastDFS
        String[] upload = FastDFSUtil.upload(fastFDSFile);
        String url = "http://192.168.50.4:8080/" + upload[0] + "/" + upload[1];
        return new Result(true, StatusCode.OK,"文件上传成功!",url);
    }
}
