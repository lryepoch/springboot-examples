package com.authority.security.component;

import com.authority.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lryepoch
 * @date 2020/12/28 16:00
 * @description TODO JWT登录授权过滤器(发起请求：1)。 设置用户信息到上下文中
 *                   使用的是OncePerRequestFilter，目的是为了保证每次request 只触发一次filter
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);

        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            // authToken is the part after "Bearer "
            String authToken = authHeader.substring(this.tokenHead.length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            LOGGER.info("1.从token中获取的用户名称为: {}", username);

            //SecurityContextHolder.getContext().getAuthentication()读取身份验证对象（用户登录的时候设置好了），比缓存要快
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                //判断token中的username和数据库中的username是否一致
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info("1.在authenticationToken设置的用户名为: {}", userDetails.getUsername());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        //将请求转发给过滤器链上的下一个对象。这里的下一个指的是下一个filter，如果没有filter那就是你请求的资源。 一般filter都是一个链
        filterChain.doFilter(request, response);
    }
}
