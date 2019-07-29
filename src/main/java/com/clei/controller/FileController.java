package com.clei.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping("file")
@RestController
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping("upload")
    public String upload(@RequestParam(value = "file1") MultipartFile file1,
                         @RequestParam(value = "file2") MultipartFile file2,
                         String token) throws Exception {
        JSONObject object = (JSONObject) JSONObject.parse(token);
        logger.info(object.getString("token"));
        if(null != file1){
            write(file1);
            write(file2);
            return "success";
        }else{
            return "empty";
        }
    }

    private void write(MultipartFile file) throws IOException {
        if(null != file){
            if(file.isEmpty()){
                return;
            }
            String fileName = file.getOriginalFilename();
            // file 文件夹等不存在 它不会自动创建。。会报错
            file.transferTo(new File("E:\\file\\" + fileName));
            // write(fileName,file.getInputStream());
        }
    }


    private void write(String fileName,InputStream is) throws Exception{
        File file = new File("E:\\" + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = is.read(bytes)) != -1){
            System.out.println(length);
            fos.write(bytes);
        }
        is.close();
        fos.close();
    }
}
