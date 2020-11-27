package com.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
* @Description
* @Author       lryepoch
* @CreateDate   2020/11/27 21:26
*/
@Service
public class AsyncService {

    /**
    * @description 基于@Async标注的方法，称之为异步方法；这些方法将在执行的时候，
     *              将会在独立的线程中被执行，调用者无需等待它的完成，即可继续其他的操作。
     *              （@Async修饰的方法不要定义为static，否则异步调用不会生效）
     *
     *              在类上面添加@Async表示所有方法都会异步，在方法上添加表示该方法异步。
     *
    * @author      lryepoch
    * @date        2020/11/27 21:31
    */
    @Async
    public void hello(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
