package com.shiro.config;

import com.shiro.entity.Permission;
import com.shiro.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lryepoch
 * @date 2020/5/19 15:35
 * @description TODO shiro配置类
 */
@Configuration
@Slf4j
public class ShiroConfig {
    @Autowired
    private PermissionService permissionService;

    private static ShiroConfig shiroConfig;

    @PostConstruct
    public void init(){
        shiroConfig = this;
        shiroConfig.permissionService = this.permissionService;
    }

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
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置未认证(登录)时，访问需要认证的资源时跳转的页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

//        // 添加shiro的内置过滤器
//        /**
//         * anon:无需认证就可以访问
//         * authc:必须认证了才能够访问
//         * user:用户拦截器，用户已经身份验证/记住我登录的都可
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

        //授权过滤器
        //注意：当前授权拦截后，shiro会自动跳转到未授权页面
//        filterChainDefinitionMap.put("/listProduct", "roles[admin,productManager]");
//        filterChainDefinitionMap.put("/deleteProduct", "roles[admin,productManager]");
//        filterChainDefinitionMap.put("/deleteOrder", "roles[admin,productManager]");

        //注册数据库中所有的权限及其对应url
//        List<Permission> allPermission = shiroConfig.permissionService.findAll();//数据库中查询所有权限
//        for (Permission p : allPermission) {
//            filterChainDefinitionMap.put(p.getUrl(), "perms[" + p.getName() + "]");    //拦截器中注册所有的权限
//        }

        //其他路径则需要登录才能访问
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(getMyShiroRealm());
        return securityManager;
    }

    @Bean
    public MyShiroRealm getMyShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理，所以我们需要修改下doGetAuthenticationInfo中的代码）
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    //自定义密码比较器 new CredentialsMatcher()
    public class CredentialsMatcher extends SimpleCredentialsMatcher {
        @Override
        public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
            UsernamePasswordToken utoken = (UsernamePasswordToken) token;
            //所需加密的参数，即用户输入的密码
            String source = String.valueOf(utoken.getPassword());
            log.info("用户输入的密码:{}", source);
            //[盐] 一般为用户名或随机数
            String salt = utoken.getUsername();
            log.info("salt值:{}", salt);
            //加密次数
            int hashIterations = 2;
            SimpleHash sh = new SimpleHash("md5", source, salt, hashIterations);
            String Strsh = sh.toHex();
            //打印最终结果
            log.info("加密后的密码：" + Strsh);
            //获得数据库中的密码
            String dbPassword = (String) getCredentials(info);
            log.info("而数据库中密码：" + dbPassword);
            //进行密码的比对
            return this.equals(Strsh, dbPassword);
        }
    }

    /**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁，主要是AuthorizingRealm类的子类，以及EhCacheManager类
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理
     *
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
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
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
