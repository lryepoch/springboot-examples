package com.authority.config;

import com.authority.common.config.BaseSwaggerConfig;
import com.authority.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lryepoch
 * @date 2020/12/25 19:31
 * @description TODO Swagger API文档相关配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    /**
     * 添加摘要信息
     */
    @Override
    public SwaggerProperties swaggerProperties() {

        //构造一个SwaggerProperties对象
        return SwaggerProperties.builder()
                .apiBasePackage("com.authority.modules")
                .title("mall-authority脚手架接口文档")
                .description("mall-authority脚手架相关接口文档")
                .contactName("lryepoch")
                .contactEmail("lryepoch@163.com")
                .contactUrl("www.github.com/lryepoch")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }

}
