package com.authority.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/27 17:31
 * @Description MyBatis配置类
 */
//@Configuration
//@EnableTransactionManagement
//@MapperScan({"com.authority.modules.*.mapper"})
//public class MyBatisConfig {
//
//    @Bean
//    public PaginationInterceptor paginationInterceptor(){
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
//        return paginationInterceptor;
//    }
//}
