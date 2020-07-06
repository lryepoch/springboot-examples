package com.schedule.configurerSchedule;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.util.concurrent.*;

/**
 * @author lryepoch
 * @date 2020/7/6 16:26
 * @description TODO 基于接口SchedulingConfigurer的动态定时任务
 */
@Configuration
@EnableScheduling
public abstract class ConfigurerScheduling implements SchedulingConfigurer {

    /*定时任务名称*/
    private String schedulerName;

    /*定时任务周期表达式*/
    private String cron;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskScheduler());
        scheduledTaskRegistrar.addTriggerTask(
                //执行定时任务
                () -> {
                    processTask();
                },
                //设置触发器
                triggerContext -> {
                    //初始化定时任务周期
                    if (StringUtils.isEmpty(cron)) {
                        cron = getCron();
                    }
                    CronTrigger trigger = new CronTrigger(cron);
                    return trigger.nextExecutionTime(triggerContext);
                }
        );
    }

    /**
     * 设置TaskScheduler用于注册计划任务
     *
     */
    @Bean(destroyMethod = "shutdown")
    public Executor taskScheduler() {
        //设置线程名称
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        //创建线程池
        return Executors.newScheduledThreadPool(5, namedThreadFactory);
    }

    /**
     * 本函数需要由派生类根据业务逻辑实现
     */
    protected abstract void processTask();

    /**
     * 获取定时任务周期表达式
     * 本函数有派生类实现，从配置文件，数据库等方式获取参数值
     */
    protected abstract String getCron();

}
