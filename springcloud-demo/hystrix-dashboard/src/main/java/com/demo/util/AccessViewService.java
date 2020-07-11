package com.demo.util;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;

/**
 * @author lryepoch
 * @date 2020/7/9 11:47
 * @description TODO 这是一个不断访问的类。只有不断地访问服务，才便于在监控那里观察现象
 */
public class AccessViewService {
    public static void main(String[] args) {
//        while (true) {
//            try {
//                ThreadUtil.sleep(1000);
//                String html = HttpUtil.get("http://127.0.0.1:8012/list");
//                System.out.println("html length:" + html.length());
//            } catch (Exception e) {
//                System.err.println(e.getMessage());
//            }
//        }

        /**
        * @description 修改AccessViewService， 使得其访问 8012和 8013端口
        * @author lryepoch
        * @date 2020/7/9 14:08
        *
        */
        while (true) {
            ThreadUtil.sleep(1000);
            access(8012);
            access(8013);
        }
    }

    public static void access(int port) {
        try {

            String html = HttpUtil.get(String.format("http://127.0.0.1:%d/list", port));
            System.out.printf("%d 地址的视图服务访问成功，返回大小是%d%n", port, html.length());

        } catch (Exception e) {
            System.err.printf("%d 地址的视图服务无法访问%n", port);
        }
    }
}
