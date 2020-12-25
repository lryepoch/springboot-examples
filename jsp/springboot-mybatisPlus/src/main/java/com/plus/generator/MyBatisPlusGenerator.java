package com.plus.generator;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MyBatisPlus代码生成器
 */
public class MyBatisPlusGenerator {

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        System.out.println("当前项目路径：" + projectPath);
//        String moduleName = scanner("模块名");
        String[] tableNames = scanner("表名，多个英文逗号分割").split(",");

        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        //1.初始化全局配置
        autoGenerator.setGlobalConfig(initGlobalConfig(projectPath));
        //2.初始化数据源配置
        autoGenerator.setDataSource(initDataSourceConfig());
        //3.初始化包配置
        autoGenerator.setPackageInfo(initPackageConfig());
        //4.初始化自定义配置
        autoGenerator.setCfg(initInjectionConfig(projectPath));
        //5.初始化模板配置
        autoGenerator.setTemplate(initTemplateConfig());
        //6.初始化策略配置
        autoGenerator.setStrategy(initStrategyConfig(tableNames));
        //默认模板引擎
        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());
        //执行生成
        autoGenerator.execute();
    }

    /**
     * 读取控制台内容信息
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (StrUtil.isNotEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 初始化全局配置
     */
    private static GlobalConfig initGlobalConfig(String projectPath) {
        GlobalConfig globalConfig = new GlobalConfig();
        //生成文件的输出目录
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("lryepoch");
        //是否打开输出目录，默认为true
        globalConfig.setOpen(false);
        ////实体属性是否swagger2注解
        globalConfig.setSwagger2(true);
        //生成ResultMap
        globalConfig.setBaseResultMap(true);
        //是否覆盖已有文件
        globalConfig.setFileOverride(true);
        globalConfig.setDateType(DateType.ONLY_DATE);
        //自定义文件命名，注意 %s 会自动填充表实体属性！
        globalConfig.setEntityName("%s");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        return globalConfig;
    }

    /**
     * 初始化数据源配置
     */
    private static DataSourceConfig initDataSourceConfig() {
        Props props = new Props("classpath:/generator.properties");
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(props.getStr("dataSource.url"));
        dataSourceConfig.setDriverName(props.getStr("dataSource.driverName"));
        dataSourceConfig.setUsername(props.getStr("dataSource.username"));
        dataSourceConfig.setPassword(props.getStr("dataSource.password"));
        return dataSourceConfig;
    }

    /**
     * 初始化包配置
     */
    private static PackageConfig initPackageConfig() {
        Props props = new Props("generator.properties");
        PackageConfig packageConfig = new PackageConfig();
//        packageConfig.setModuleName(moduleName);
        packageConfig.setParent(props.getStr("package.base"));
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setMapper("dao");
        packageConfig.setEntity("entity");
        packageConfig.setXml("mapping");
        return packageConfig;
    }

    /**
     * 初始化自定义配置
     */
    private static InjectionConfig initInjectionConfig(String projectPath) {
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // 可用于自定义属性
            }
        };
//        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";

        // 模板引擎是Velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapping/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        return injectionConfig;
    }

    /**
     * 初始化模板配置
     */
    private static TemplateConfig initTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        //可以对controller、service、entity模板进行配置
        //mapper.xml模板需单独配置
        templateConfig.setXml(null);
        return templateConfig;
    }

    /**
     * 初始化策略配置
     */
    private static StrategyConfig initStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();

//        // 此处可以修改为您的表前缀
//        strategyConfig.setTablePrefix(new String[]{"t_"});

        // 数据库表映射到实体的命名策略
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 是否使用Lombok简化代码
        strategyConfig.setEntityLombokModel(true);
        //生成@RestController控制器
        strategyConfig.setRestControllerStyle(true);
        //当表名中带*号时可以启用通配符模式
        if (tableNames.length == 1 && tableNames[0].contains("*")) {
            String[] likeStr = tableNames[0].split("_");
            String likePrefix = likeStr[0] + "_";
            // 模糊匹配表名
            strategyConfig.setLikeTable(new LikeTable(likePrefix));
        } else {
            // 需要生成的表
            strategyConfig.setInclude(tableNames);
//            // 排除的表名
//            strategyConfig.setExclude("upms_role");
        }
        return strategyConfig;
    }

}