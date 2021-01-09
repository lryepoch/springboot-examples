package com.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lryepoch
 * @date 2020/5/26 9:33
 * @description TODO 项目地址：http://localhost:8080/
 */
@Controller
@Slf4j
public class UploadController {

    private static String UPLOADED_FOLDER = "E://temp//";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        //上传文件不能为空
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "输入文件不能为空");
            return "redirect:/uploadStatus";
        }

//        String UPLOADED_FOLDER = request.getServletContext().getRealPath("")+"static"+ File.separator;
//        log.info("+++++++++++"+request.getServletPath());
//        log.info(request.getServletContext().getRealPath(""));
//        log.info(File.separator);

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            log.info(String.valueOf(path.getFileName()));
            attributes.addFlashAttribute("message", "上传成功");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}
