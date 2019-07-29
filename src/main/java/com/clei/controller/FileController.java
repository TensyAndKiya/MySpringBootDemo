package com.clei.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RequestMapping("file")
@RestController
public class FileController {
    @RequestMapping("upload")
    public String upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(null != file){
            System.out.println(request.getContextPath());
            if(file.isEmpty()){
                return "empty";
            }
            String fileName = file.getOriginalFilename();
            // file 文件夹等不存在 它不会自动创建。。会报错
            file.transferTo(new File("E:\\file\\" + fileName));
            // write(fileName,file.getInputStream());
            return "success";
        }
        return "empty";
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
