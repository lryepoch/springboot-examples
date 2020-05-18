package com.shiro.config;

import com.shiro.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lryepoch
 * @date 2020/5/18 18:41
 * @description TODO
 */
public class MyShiroRealm extends AuthorizingRealm {
    /**
     * 授权
     * doGetAuthorizationInfo方法是在我们调用
     * SecurityUtils.getSubject().isPermitted（）这个方法，
     * 授权后用户角色及权限会保存在缓存中的
     *
     * @param principal
     * @return
     * @RequiresPermissions这个注解起始就是在执行SecurityUtils.getSubject().isPermitted（）
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        user = userService.findByUsername(user.getName());
        Set<String> userRoles = new HashSet<>();
        Set<String> userPermissions = new HashSet<>();
        for (Role role:user.getRoles()){
            userRoles.add();
        }
        return null;
    }

    /**
     * 认证.登录
     * doGetAuthenticationInfo这个方法是在用户登录的时候调用的
     * 也就是执行SecurityUtils.getSubject().login()的时候调用；(即:登录验证)
     * 验证通过后会用户保存在缓存中的
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();

        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,//用户信息
                user.getPassword(),//密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName());//realm name
        return authenticationInfo;
    }
}
