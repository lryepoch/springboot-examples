package com.authority.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 *
 * JWT token的格式：header.payload.signature
 *
 * 1.header的格式（算法、token的类型）： {"alg": "HS512","typ": "JWT"}
 * 2.payload的格式（用户名、创建时间、生成时间）： {"sub":"wang","created":1489079981393,"exp":1489684781}
 * 3.signature的生成算法： HMACSHA512(base64UrlEncode(header) + "." + base64UrlEncode(payload),secret)
 *
 */
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    //用户名
    private static final String CLAIM_KEY_USERNAME = "sub";
    //创建时间
    private static final String CLAIM_KEY_CREATED = "created";
    //秘钥
    @Value("${jwt.secret}")
    private String secret;
    //过期时间
    @Value("${jwt.expiration}")
    private Long expiration;
    //JWT负载中拿到开头
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据负责生成JWT的token
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)//sub+created
                .setExpiration(generateExpirationDate())//exp
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成token的过期时间(过期时长统一设置了)
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 当原来的token没过期是可以刷新的
     */
    public String refreshHeadToken(String oldToken) {
        if (StrUtil.isEmpty(oldToken)) {
            return null;
        }
        String token = oldToken.substring(tokenHead.length());
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        //token检验不通过
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        //如果token已经过期，不支持刷新
        if (isTokenExpired(token)) {
            return null;
        }
        //如果token在30分钟之内刚刷新过，返回原token
        if (tokenRefreshJustBefore(token, 30 * 60)) {
            return token;
        } else {
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }
    }

    /**
     * 从token中获取JWT中的负载payload
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)//jwt加解密的秘钥
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败：{}", token);
        }
        return claims;
    }

//    public static void main(String[] args) {
//        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJjcmVhdGVkIjoxNjEwMDA0OTU4Mjc4LCJleHAiOjE2MTA2MDk3NTh9.9xACZvyZO4ZvdM5-bJQ3YcH9_SRQs3F4_yD521fBlErgQJ7biW9BPSF2uNPUmU9hEWxFIXX4ADYOCJbe9CneTQ";
//        Claims claims = Jwts.parser().setSigningKey("mall-admin-secret")
//                            .parseClaimsJws(token)
//                            .getBody();
//        System.out.println(claims); //{sub=string, created=1610004958278, exp=1610609758}
//    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     *
     * @param token 原token
     * @param time  指定时间（秒）30*60s
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if (refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, time))) {
            return true;
        }
        return false;
    }

    /**
     * 判断token是否已经失效（过期时间是否已到）
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效（1.用户名是否一致，2.时间是否过期）
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
