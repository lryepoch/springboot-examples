package com.shiro.config;

import com.shiro.entity.User;
import com.shiro.service.PermissionService;
import com.shiro.service.RoleService;
import com.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/5/18 18:41
 * @description TODO
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 授权
     * doGetAuthorizationInfo方法是在我们调用
     * SecurityUtils.getSubject().isPermitted（）这个方法，
     * 授权后用户角色及权限会保存在缓存中的
     *
     * @param principals
     * @return
     * @RequiresPermissions这个注解起始就是在执行SecurityUtils.getSubject().isPermitted（）
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("进入用户授权模块……");
//        User user = (User) principals.getPrimaryPrincipal();
        //从PrincipalCollection中获取登录用户的信息
        String username = (String) getAvailablePrincipal(principals);
//        user = userService.findByUsername(username);

        List<String> roles = roleService.listRoleNames(username);
        List<String> permissions = permissionService.listPermissionsNames(username);
        log.info("roles：{}", roles);
        log.info("permissions：{}", permissions);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 认证.登录
     * doGetAuthenticationInfo这个方法是在用户登录的时候调用的
     * 也就是执行SecurityUtils.getSubject().login()的时候调用；(即:登录验证)
     * 验证通过后会用户保存在缓存中的
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("进入用户登录模块……");
        String username = (String) token.getPrincipal();

        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        log.info("username: {}", username);
        log.info("user：{}", user);
        log.info("user.getName()：{}", user.getName());
        log.info("user.getPassword()：{}", user.getPassword());
        log.info("user.getSalt()：{}", user.getSalt());
        log.info("ByteSource.Util.bytes(user.getSalt())：{}", ByteSource.Util.bytes(user.getSalt()));
        log.info("getName()：{}", getName());

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getName(),//用户名
                user.getPassword(),//密码
                ByteSource.Util.bytes(user.getSalt()),//salt=username+salt
                getName());//realm name
        return authenticationInfo;
    }
}
