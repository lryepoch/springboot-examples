package com.how2j.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.HashSet;

public class MultiThreadFileController {

//    public static BlockingQueue blockingQueue = new LinkedBlockingQueue(100);
//
//    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 100, 10, TimeUnit.SECONDS, blockingQueue);
//
//    @RequestMapping("/travel2")
//    public static void travelFolder(HttpServletResponse response) {
//
//        File file = new File("C:\\Users\\260408\\Desktop\\我的文件夹");
//        ArrayList<File> list = new ArrayList<>();
//        list.add(file);
//
//        for(int i = 0; i < list.size(); i++) {
//            if (list.get(i).isDirectory()) {
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        File[] files = list.get(i).listFiles();
//                        for (File f1 : files) {
//                            list.add(f1);
//                        }
//                    }
//                }).start();
//            }
//        }
//
//        for (File f2 : list) {
//            //如果是文件，则切割文件路径，输出相对路径
//            if (!f2.isDirectory()) {
//
//                String str = f2.toString();
//                String[] strings = str.split("\\\\");
//                int i = strings.length;
//                System.out.println("/" + strings[i - 2] + "/" + strings[i - 1]);
//
//            }
//
//        }
//        System.out.println("------------------------");
//
//    }


//   @RequestMapping("/travel3")
//    public void travelFile(HttpServletResponse response) {
//
//        File file = new File("C:\\\\Users\\\\260408\\\\Desktop\\\\我的文件夹");
//
//        LinkedList<File> list = new LinkedList<>();
//
//        list.push(file);
//
//        ArrayList<String> alist = new ArrayList<>();
//
//        while (!list.isEmpty()) {
//            File f1 = list.pop();
//
//            if(!f1.isDirectory()) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String string = f1.toString();
//                        String[] strings = string.split("\\\\");
//                        int i = strings.length;
//                        //System.out.println(strings[i-1] + "  " + "/" + strings[i-2]);
//                        alist.add(strings[i-1] + "  " + "/" + strings[i-2]);
//                    }
//                }).start();
//
//            } else {
//
//                File[] files = f1.listFiles();
//                for (File f2 : files) {
//                    list.push(f2);
//                }
//
//            }
//        }
//
//        downloadTxt(alist, response);
//
//    }


    HashSet<String> alist = new HashSet<>();

    @RequestMapping("/travel")
    public void travel(HttpServletResponse response) {

        File file = new File("E:\\IdeaProjects");

        File[] files = file.listFiles();

        for (File f1 : files) {
            if (f1.isDirectory()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        transfer(f1);
                    }
                }).start();
            } else {
                String str = f1.toString();
                String[] strings = str.split("\\\\");
                int i = strings.length;
                //System.out.println(strings[i - 1] + "  " + "/" + strings[i - 2]);
                alist.add(strings[i - 1] + "  " + "/" + strings[i - 2]);
            }
        }

        System.out.println("-----------------------");
        for (String s : alist) {
            System.out.println(s);
        }

        downloadTxt(response, alist);

    }


    public void transfer(File file) {

        File[] files = file.listFiles();

        for (File f1 : files) {
            if (f1.isDirectory()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        transfer(f1);
                    }
                }).start();

            } else {
                String str = f1.toString();
                String[] strings = str.split("\\\\");
                int i = strings.length;
                //System.out.println(strings[i-1] + "  " + "/" +strings[i-2]);
                alist.add(strings[i - 1] + "  " + "/" + strings[i - 2]);
            }
        }
    }


    public void downloadTxt(HttpServletResponse response, HashSet<String> alist) {

        String filename = "multiFile.txt";

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename = " + filename);

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
