package com.upload.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
* @description
* @author lryepoch
* @date 2020/6/30 14:51 文件（图片）下载   http://localhost:8082/download    ，直接下载
*
*/
@RestController
public class DownloadController {

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String filePath = request.getSession().getServletContext().getRealPath("/") + "uploaded\\";
        System.out.println(request.getSession());
        System.out.println(request.getSession().getServletContext());
        System.out.println(request.getSession().getServletContext().getRealPath("/"));
        System.out.println("------------------------------");

        String fileName = "1556981245241微信图片_20170909010049.jpg";

        File file = new File(filePath + fileName);

        if (file.exists()) {
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;

            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                ServletOutputStream outputStream = response.getOutputStream();

                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return "下载成功";

            } catch (Exception e) {
                e.printStackTrace();

            } finally {

                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return "下载失败";
    }
}
