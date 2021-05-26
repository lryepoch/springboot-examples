package com.authority.security.config;

import com.authority.security.component.*;
import com.authority.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/27 17:49
 * @Description TODO 对Spring Security的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired(required = false)
    private DynamicSecurityService dynamicSecurityService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        logger.info("进入SecurityConfig -> configure(HttpSecurity httpSecurity)");

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
                .authorizeRequests();
        //禁用缓存
//        httpSecurity.headers().cacheControl().disable();

        //不需要保护的资源路径允许访问，此处配置目的是为了过滤swagger等静态资源，与/admin/login等无关
        for (String url : ignoreUrlsConfig().getUrls()) {
            logger.info("0.放行的url:{}", url);
            registry.antMatchers(url).permitAll();
        }
        //允许跨域请求的OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS)
                .permitAll();

        //接下来的任何请求需要身份认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()

                //关闭跨站请求防护及不使用session
                //采用JWT的方式进行认证首先做的第一步就是在配置类里禁用掉session。这里的禁用是指Spring Security不采用session机制了，不代表你禁用掉了整个系统的session功能
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

//                //退出登录
//                .and()
//                .logout()
//                .logoutUrl("logout")
//                .logoutSuccessHandler(onLogoutSuccess())
//                .permitAll()

                //自定义权限拒绝处理类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler())
                .authenticationEntryPoint(restAuthenticationEntryPoint())

                // 将我们自定义的JWT认证过滤器插入到默认的认证过滤器之前
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //自定义权限过滤器完成后，回到Spring Security配置类让这个过滤器插入到原有的默认鉴权过滤器之前
        if (dynamicSecurityService != null) {
            registry.and()
                    .addFilterBefore(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("进入SecurityConfig -> configure(AuthenticationManagerBuilder auth)");
        //方法userDetailsService()，存在于类MallSecurityConfig中。
        //作用是获取用户信息，比如从数据库中获取。AuthenticationManager在认证用户身份信息的时候，就会从中获取用户身份, 和从http中拿的用户身份做对比。
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    /**
     * public interface PasswordEncoder {
     * String encode(CharSequence rawPassword);
     * boolean matches(CharSequence rawPassword, String encodedPassword);
     * default boolean upgradeEncoding(String encodedPassword) {
     * return false;
     * }
     * }
     * encode 方法用来对明文密码进行加密，返回加密之后的密文。
     * matches 方法是一个密码校对方法，在用户登录的时候，将用户传来的明文密码和数据库中保存的密文密码作为参数，传入到这个方法中去，根据返回的 Boolean 值判断用户密码是否输入正确。
     * upgradeEncoding 是否还要进行再次加密，这个一般来说就不用了。
     * <p>
     * 配置这个，控制台将不会再产生随机密码了。同时会覆盖yml文件配置的自定义用户和密码
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("加载 密码加密的Bean。PasswordEncoder passwordEncoder()");
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager接口对用户的未授信凭据进行认证，认证通过则返回授信状态的凭据，否则将抛出认证异常
     * AuthenticationManager 就是Spring Security用于执行身份验证的组件，只需要调用它的authenticate方法即可完成认证
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        logger.info("加载 AuthenticationManager authenticationManagerBean()");
        return super.authenticationManagerBean();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        logger.info("加载无访问权限Bean。RestfulAccessDeniedHandler restfulAccessDeniedHandler()");
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        logger.info("加载未登录或登录过期Bean。RestAuthenticationEntryPoint restAuthenticationEntryPoint()");
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public MyLogoutSuccessHandler onLogoutSuccess() {
        logger.info("加载退出登录的Bean。MyLogoutSuccessHandler onLogoutSuccess()");
        return new MyLogoutSuccessHandler();
    }

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        logger.info("加载URL白名单的Bean。IgnoreUrlsConfig ignoreUrlsConfig()");
        return new IgnoreUrlsConfig();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        logger.info("加载JWT的Bean。JwtTokenUtil jwtTokenUtil()");
        return new JwtTokenUtil();
    }


    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        logger.info("加载 1登录认证过滤器，保存用户信息到上下文的Bean。JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter()");
        return new JwtAuthenticationTokenFilter();
    }

    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        logger.info("加载 4对比请求的资源和拥有的资源的Bean。DynamicAccessDecisionManager dynamicAccessDecisionManager()");
        return new DynamicAccessDecisionManager();
    }


    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter() {
        logger.info("加载 2过滤掉不需拦截的资源权限Bean。DynamicSecurityFilter dynamicSecurityFilter()");
        return new DynamicSecurityFilter();
    }

    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        logger.info("加载 3将当前有访问权限的的资源加入map中并返回的Bean。DynamicSecurityMetadataSource dynamicSecurityMetadataSource()");
        return new DynamicSecurityMetadataSource();
    }

}
