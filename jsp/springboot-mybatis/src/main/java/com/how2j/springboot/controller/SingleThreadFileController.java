package com.how2j.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class SingleThreadFileController {

    /*    @RequestMapping("/test1")
    private void test(HttpServletResponse response, File file) {

        ArrayList<String> alist = new ArrayList<>();

        File[] list = file.listFiles();

        for (File file2 : list) {
            if (file2.isDirectory()) {
                test(response, file2);

                //如果是文件，则切割文件路径，输出相对路径
            }else {
                String str = file2.toString();
                String[] strings = str.split("\\\\");
                int i = strings.length;
                System.out.println("/" + strings[i-2] + "/" + strings[i-1]);
                alist.add("/" + strings[i-2] + "/" + strings[i-1]);
            }

        }
        putTxt(response, alist);
    }*/


    @RequestMapping("/test")
    public void test(HttpServletResponse response) {

        File file = new File("C:\\Users\\260408\\Desktop\\我的文件夹");

        LinkedList<File> list = new LinkedList<>();

        list.push(file);


        //先保存遍历结果，再通过输出流下载到txt文件
        ArrayList<String> alist = new ArrayList<>();

        //list集合
        while (!list.isEmpty()) {

            File f1 = list.pop();

            //如果是目录，压栈
            if (f1.isDirectory()) {
                File[] files = f1.listFiles();

                for (File f2 : files) {
                    list.push(f2);
                }
            }

            //如果是文件，则切割文件路径，输出相对路径
            if (!f1.isDirectory()) {

                String str = f1.toString();
                String[] strings = str.split("\\\\");
                int i = strings.length;
                System.out.println(strings[i - 1] + "  " + "/" + strings[i - 2]);

                //先把遍历出来的结果添加到集合中，再在后面putTxt()方法中一次性写入txt文件
                alist.add(strings[i - 1] + "  " + "/" + strings[i - 2]);
            }
        }

        //response只能用于一次请求
        putTxt(response, alist);
    }

    public void putTxt(HttpServletResponse response, ArrayList<String> alist) {

        String fileName = "file.txt";

        //以纯文本的形式
        response.setContentType("text/plain");
        //设置响应头，以下载文件的形式打开文件
        response.setHeader("Content-Disposition", "attachment; filename = " + fileName);

        try {

            OutputStream outputStream = response.getOutputStream();

            BufferedOutputStream buffer = new BufferedOutputStream(outputStream);

            for (String s : alist) {

                buffer.write((s + "\r\n").getBytes("UTF-8"));
            }

            buffer.flush();

            buffer.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
