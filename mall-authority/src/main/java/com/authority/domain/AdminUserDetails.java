package com.authority.domain;

import com.authority.modules.entity.UmsAdmin;
import com.authority.modules.entity.UmsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lryepoch
 * @date 2020/12/28 11:28
 * @description TODO Spring Security需要的用户详情。UserDetails接口实现仅仅存储用户的信息
 * <p>
 * UserDetails 默认提供了：
 * 用户的权限集，默认需要添加ROLE_ 前缀
 * 用户的加密后的密码，不加密会使用{noop}前缀
 * 应用内唯一的用户名
 * 账户是否过期
 * 账户是否锁定
 * 凭证是否过期
 * 用户是否可用
 */
public class AdminUserDetails implements UserDetails {
    private static final Logger logger = LoggerFactory.getLogger(AdminUserDetails.class);

    private UmsAdmin umsAdmin;
    private List<UmsResource> resourceList;

    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsResource> resourceList) {
        this.umsAdmin = umsAdmin;
        this.resourceList = resourceList;
    }

    /**
     * 用户权限集
     * UserDeitails接口里面有一个getAuthorities()方法。这个方法将返回此用户的所拥有的资源权限。这个集合将用于用户的访问控制，也就是Authorization
     * <p>
     * 在security中，角色和权限共用GrantedAuthority接口，唯一的不同角色就是多了个前缀"ROLE_"，而且它没有shiro的那种从属关系，即一个角色包含哪些权限等等。
     * 在security看来角色和权限是一样的，它认证的时候，把所有权限（角色、权限）都取出来，而不是分开验证。
     * <p>
     * 所以，在Security提供的UserDetailsService默认实现JdbcDaoImpl中，角色和权限都存储在auhtorities表中。而不是像Shiro那样，角色有个roles表，
     * 权限有个permissions表。以及相关的管理表等等。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限集
        List<SimpleGrantedAuthority> collect = resourceList.stream()
                .map(umsResource -> new SimpleGrantedAuthority(umsResource.getId() + ":" + umsResource.getName()))
                .collect(Collectors.toList());

        for (SimpleGrantedAuthority simpleGrantedAuthority : collect) {
            logger.info("AdminUserDetails->getAuthorities()，返回当前用户的权限集：{}", simpleGrantedAuthority);
        }
        return collect;
    }

    /**
     * 密码
     */
    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    /**
     * 用户名
     */
    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    /**
     * 账户是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否可用
     */
    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
