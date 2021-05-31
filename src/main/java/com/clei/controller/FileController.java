package com.clei.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RequestMapping("/file")
@Controller
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping("/view")
    public String view() {
        return "file";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file1") MultipartFile file1,
                         @RequestParam(value = "file2") MultipartFile file2,
                         @RequestParam(value = "token") String token) throws Exception {
        JSONObject object = JSONObject.parseObject(token);
        logger.info(object.getString("token"));
        if (null == file1 && null == file2) {
            return "all null";
        }
        if (null != file1 && file1.isEmpty() && null != file2 && file2.isEmpty()) {
            return "all empty";
        }
        write(file1);
        write(file2);
        return "success";
    }

    private void write(MultipartFile file) throws Exception {
        if (null == file || file.isEmpty()) {
            return;
        }
        String fileName = file.getOriginalFilename();
        // file.transferTo(new File("E:\\file\\" + fileName));
        write(fileName, file.getInputStream());
    }


    private void write(String fileName, InputStream is) throws Exception {
        File file = new File("E:\\" + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = is.read(bytes)) != -1) {
            fos.write(bytes, 0, length);
        }
        is.close();
        fos.close();
        logger.info("成功写入: {}", fileName);
    }
}
