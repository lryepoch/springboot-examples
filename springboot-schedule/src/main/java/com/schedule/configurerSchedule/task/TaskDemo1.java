package com.schedule.configurerSchedule.task;

import com.schedule.configurerSchedule.ConfigurerScheduling;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author lryepoch
 * @date 2020/7/6 19:18
 * @description TODO
 */
@Configuration
public class TaskDemo1 extends ConfigurerScheduling {

    @Value(value = "${task.taskName1.switch}")
    private Boolean isSwitch;

    @Value(value = "${task.taskName1.cron}")
    private String cron;

    @Override
    protected void processTask() {
        if (isSwitch) {
            System.out.println("基于接口SchedulingConfigurer的动态定时任务：" +
                    LocalDateTime.now() + "，线程名称：" + Thread.currentThread().getName() +
                    "，线程id:" + Thread.currentThread().getId());
        }
    }

    @Override
    protected String getCron() {
        return cron;
    }
}
