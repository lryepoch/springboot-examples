package com.thymeleaf.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.thymeleaf.config.filter.RolesFilter;
import com.thymeleaf.config.filter.URLPathMatchingFilter;
import com.thymeleaf.entity.Permission;
import com.thymeleaf.entity.PermissionExample;
import com.thymeleaf.entity.User;
import com.thymeleaf.entity.UserExample;
import com.thymeleaf.mapper.PermissionMapper;
import com.thymeleaf.mapper.UserMapper;
import com.thymeleaf.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;


/**
 * @author lryepoch
 * @date 2020/5/19 15:35
 * @description TODO shiro配置类
 */
@Configuration
@Slf4j
//@AutoConfigureAfter(LifecycleBeanPostProcessor.class)
public class ShiroConfig {

    //    @PostConstruct
//    public void init() {
//        UserExample userExample = new UserExample();
//        userExample.or().andNameEqualTo("zhang3");
//        List<User> users = userMapper.selectByExample(userExample);
//        salt1 = users.get(0).getSalt();
//        log.info("从数据库查询出来的盐值：" + salt1);
//    }

    @Autowired
    PermissionService permissionService;


    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        log.info("进入ShiroConfig->ShiroFilterFactoryBean");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置未认证(登录)时，访问需要认证的资源时跳转登录url
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后要跳转首页url
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权url
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

//        // 添加shiro的内置过滤器
//        /**
//         * anon:无需认证就可以访问
//         * authc:必须认证了才能够访问
//         * user:用户拦截器，用户已经身份验证/记住我登录的都可访问
//         * perms:该资源必须拥有对某个资源的权限才能访问
//         * role:该资源必须拥有某个角色权限才能访问
//         */

        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //登录
        filterChainDefinitionMap.put("/login", "anon");
        //配置不会被拦截的链接，顺序判断相关静态资源
        filterChainDefinitionMap.put("/static/**", "anon");
        //登出
        filterChainDefinitionMap.put("/doLogout", "logout");


//      /* 自定义filter注册 */
        /*1.角色拦截器，回调了doGetAuthorizationInfo()方法*/
//        Map<String, Filter> filterMap = new LinkedHashMap<>();
//        filterMap.put("rolesOr", roleFilter());
//        shiroFilterFactoryBean.setFilters(filterMap);
//        //由于Shiro filterChainDefinitions中 roles默认是and。比如：roles[system,general] ，表示同时需要“system”和“general” 2个角色（权限）才通过认证，缺一不可。
//        filterChainDefinitionMap.put("/listProduct", "authc, rolesOr[admin,productManager]");
//        filterChainDefinitionMap.put("/deleteProduct", "authc, rolesOr[productManager,admin]");
//        filterChainDefinitionMap.put("/deleteOrder", "authc, rolesOr[admin]");


        /* 拦截器中注册所有的权限 */
        //2.1.此种方式正常，调用了doGetAuthorizationInfo()方法
        List<Permission> permissionList = permissionService.findAll();
        for (Permission permission : permissionList) {
            filterChainDefinitionMap.put(permission.getUrl(), "perms[" + permission.getName() + "]");
            log.info("访问资源路径：" + permission.getUrl(), "需要权限：" + permission.getName());
        }
//      //2.2.此种方式正常，调用了doGetAuthorizationInfo()方法。设置了缓存，只打印一遍
//        filterChainDefinitionMap.put("/listProduct","perms[listProduct]");
//        filterChainDefinitionMap.put("/deleteProduct","perms[deleteProduct]");
//        filterChainDefinitionMap.put("/deleteOrder","perms[deleteOrder]");

//      //3.此种方式正常，但是没有回调doGetAuthorizationInfo()方法。还要注释掉下面的 filterChainDefinitionMap.put("/**", "authc");
//        Map<String, Filter> customisedFilter = new HashMap<>();
//        customisedFilter.put("url", getURLPathMatchingFilter());
//        shiroFilterFactoryBean.setFilters(customisedFilter);
//        filterChainDefinitionMap.put("/**", "url");


        //其他路径则需要登录才能访问
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    public RolesFilter roleFilter() {
        return new RolesFilter();
    }

    public URLPathMatchingFilter getURLPathMatchingFilter() {
        return new URLPathMatchingFilter();
    }


    @Bean
    public SecurityManager securityManager() {
        log.info("进入ShiroConfig->SecurityManager");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm，可以添加多个
        List<Realm> realms = new ArrayList<>();
        realms.add(getMyShiroRealm());
        securityManager.setRealms(realms);
        //自定义session管理
        securityManager.setSessionManager(defaultWebSessionManager());
        //注入缓存管理器，管理如用户、角色、权限等的缓存
        securityManager.setCacheManager(ehCacheManager());//这个如果执行多次，也是同样的一个对象;
        //注入rememberme管理器
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     *
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    /**
     * cookie管理对象;
     *
     * @return
     */
    @Bean
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
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public MyShiroRealm getMyShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
//        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
//        myShiroRealm.setAuthenticationCachingEnabled(true);
//        //缓存AuthenticationInfo信息的缓存名称, 在ehcache-shiro.xml中有对应缓存的配置
//        myShiroRealm.setAuthenticationCacheName("authenticationCache");
//        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
//        myShiroRealm.setAuthorizationCachingEnabled(true);
//        //缓存AuthorizationInfo信息的缓存名称, 在ehcache-shiro.xml中有对应缓存的配置
//        myShiroRealm.setAuthorizationCacheName("authorizationCache");
        //设置加密规则
        myShiroRealm.setCredentialsMatcher(new CredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 凭证匹配器1
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理，所以我们需要修改下doGetAuthenticationInfo中的代码）
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        //表示存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * 凭证匹配器2，自定义密码比较器 new CredentialsMatcher()
     */
    public class CredentialsMatcher extends SimpleCredentialsMatcher {
        @Override
        public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
            UsernamePasswordToken usertoken = (UsernamePasswordToken) token;

            UserExample userExample = new UserExample();
            userExample.or().andNameEqualTo(usertoken.getUsername());
            //注意getBean(String s) bean在spring中对象名小写开头
            List<User> users = ((UserMapper) ApplicationContextUtil.getBean("userMapper")).selectByExample(userExample);
            //[盐] 一般为用户名或随机数
            String salt = users.get(0).getSalt();
            log.info("==从数据库查询出来的盐值==：" + salt);

            //所需加密的参数，即用户输入的密码
            String password = String.valueOf(usertoken.getPassword());
            log.info("用户输入的密码:{}", password);

            //加密次数
            int hashIterations = 2;
            SimpleHash sh = new SimpleHash("md5", password, salt, hashIterations);
            String Strsh = sh.toHex();
            //打印最终结果
            log.info("加密后的密码：" + Strsh);

            //获得数据库中的密码
//            其中info.getCredentials()获取到的值为MyRealm中doGetAuthenticationInfo方法
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,
//                    user.getPassword(),this.getClass().getName());
//            传入的user.getPassword.
            String dbPassword = (String) getCredentials(info);
            log.info("而数据库中密码：" + dbPassword);

            //进行密码的比对
            return this.equals(Strsh, dbPassword);
        }
    }

    /**
     * LifecycleBeanPostProcessor将Initializable和Destroyable的实现类统一在其内部自动分别调用了Initializable.init()
     * 和Destroyable.destroy()方法，从而达到管理shiro bean生命周期的目的
     *
     * 该方法注释掉或者加上static修饰，程序才不会出错！！！
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理
     * <p>
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator 和 AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 开启aop注解支持
     * <p>
     * 启动Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * <p>
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * session
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(18000000);
        // url中是否显示session Id
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 删除失效的session
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationInterval(18000000);
        sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
        //设置SessionIdCookie 导致认证不成功，不重新设置新的cookie,从sessionManager获取sessionIdCookie
        //sessionManager.setSessionIdCookie(simpleIdCookie());
        sessionManager.getSessionIdCookie().setName("session-z-id");
        sessionManager.getSessionIdCookie().setPath("/");
        sessionManager.getSessionIdCookie().setMaxAge(60 * 60 * 24 * 7);
        return sessionManager;
    }

    @Bean(name = "sessionValidationScheduler")
    public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
        scheduler.setInterval(900000);
        return scheduler;
    }

    /**
     * 将ShiroDialect加入到Thymeleaf模板引擎中
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
