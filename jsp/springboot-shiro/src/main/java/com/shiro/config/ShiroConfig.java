package com.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;

import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author lryepoch
 * @date 2020/5/19 15:35
 * @description TODO shiro配置类
 */
@Configuration
@Slf4j
public class ShiroConfig {
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        //拦截器，不能使用HashMap()，时有时无，无序的
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();

        //配置映射关系
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/index", "anon");
        //配置不会被拦截的链接 顺序判断 相关静态资源
        filterChainDefinitionMap.put("/static/**", "anon");
        //配置退出其中退出的代码，shiro已经帮我们实现了
        filterChainDefinitionMap.put("/doLogout", "logout");
        //swagger
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/u/**", "anon");
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        //所有的url都必须认证通过才可以访问（注释此处，因为下面自定义了拦截器）
//        filterChainDefinitionMap.put("/**", "authc");

        //自定义拦截器(即访问自定义的url时，进行权限判断)
        Map<String, Filter> customisedFilter = new HashMap<>();
        customisedFilter.put("url", getURLPathMatchingFilter());
        log.info("customisedFilter：{}", customisedFilter);
        shiroFilterFactoryBean.setFilters(customisedFilter);
        filterChainDefinitionMap.put("/**", "url");

        log.info("filterChainDefinitionMap：{}", filterChainDefinitionMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    public URLPathMatchingFilter getURLPathMatchingFilter(){
        return new URLPathMatchingFilter();
    }

    /**
    * 安全管理器设置（多种方式调用）
    */
    @Bean
    public SecurityManager securityManager(@Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入Realm
        // 可以添加多个认证，执行顺序是有影响的
        securityManager.setRealm(getMyShiroRealm());
        // 自定义缓存实现 使用redis。redis需要序列化设置，否则出错。因此注释
//        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
//        securityManager.setSessionManager(sessionManager());
        //设置rememberMe管理器
        securityManager.setRememberMeManager(cookieRememberMeManager);
        return securityManager;
    }

    /**
    * 认证器设置
    */
    @Bean
    public MyShiroRealm getMyShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        //设置密码凭证匹配器，配置加密（在加密后，不配置的话会导致登陆密码失败）
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
//        myShiroRealm.setAuthenticationCachingEnabled(true);
//        //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
//        myShiroRealm.setAuthenticationCacheName("authenticationCache");
//        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
//        myShiroRealm.setAuthorizationCachingEnabled(true);
//        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
//        myShiroRealm.setAuthorizationCacheName("authorizationCache");
        return myShiroRealm;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * Shiro中本身就提供了sessionManager和sessionDAO，我们可以把shiro和redis集成起来，把session持久化到Redis中，需要使用的时候从Redis中可以获取对应的session。
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 记住我管理器 cookie管理对象;
     *
     * @return
     */
    @Bean(name = "cookieRememberMeManager")
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * cookie对象;
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        或者
//        simpleCookie.setName("boot-shiro-rememberMe");

        // <!-- 记住我cookie生效时间30天（259200） ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理，所以我们需要修改下doGetAuthenticationInfo中的代码）
     *
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * Shiro生命周期处理器
     *
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁，主要是AuthorizingRealm类的子类，以及EhCacheManager类
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理
     *
     * DefaultAdvisorAutoProxyCreator这个类功能更为强大，这个类的奇妙之处是他实现了BeanProcessor接口,当ApplicationContext读如所有的Bean配置信息后，
     * 这个类将扫描上下文，寻找所有的Advistor(一个Advisor是一个切入点和一个通知的组成)，将这些Advisor应用到所有符合切入点的Bean中
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 使授权注解起作用不如不想配置，可以在pom文件中加入
     * <dependency>
     *    <groupId>org.springframework.boot</groupId>
     *    <artifactId>spring-boot-starter-aop</artifactId>
     * </dependency>
     *
     *  开启shiro aop注解支持。开启@RequirePermission注解的配置
     *  使用代理方式；所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
