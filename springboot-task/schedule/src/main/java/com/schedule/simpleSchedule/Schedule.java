package com.schedule.simpleSchedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author lryepoch
 * @date 2020/7/6 16:16
 * @description TODO 基于注解(@Scheduled)的简单定时器
 *
 * cron表达式语法:[秒] [分] [小时] [日] [月] [周] [年]
 * @Scheduled(fixedDelay = 5000) //上一次执行完毕时间点之后5秒再执行
 * @Scheduled(fixedDelayString = "5000") //上一次执行完毕时间点之后5秒再执行
 * @Scheduled(fixedRate = 5000) //上一次开始执行时间点之后5秒再执行
 * @Scheduled(initialDelay=1000, fixedRate=5000) //第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
 *
 */
@Configuration
@EnableScheduling
public class Schedule {

    @Scheduled(cron = "0/5 * * * * ?")

//    或者直接指定时间间隔，例如：5秒
//    @Scheduled(fixedRate = 5000)
    private void configureTasks(){
        System.err.println("基于注解（@Scheduled）的简单定时器demo："+ LocalDateTime.now());
    }
}
