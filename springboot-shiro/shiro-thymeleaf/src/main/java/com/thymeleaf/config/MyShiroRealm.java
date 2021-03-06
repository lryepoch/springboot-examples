package com.thymeleaf.config;

import com.thymeleaf.service.PermissionService;
import com.thymeleaf.service.RoleService;
import com.thymeleaf.service.UserService;
import com.thymeleaf.entity.User;
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
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/5/18 18:41
 * @description TODO
 */
@Slf4j
@Component
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
     * @RequiresPermissions 等同于执行SecurityUtils.getSubject().isPermitted（）
     * @RequiresAuthentication 验证用户是否登录，等同于方法subject.isAuthenticated() ，它们都会回调doGetAuthorizationInfo()方法
     *
     *
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("进入用户授权模块……");
        //从PrincipalCollection中获取登录用户的信息
        String username = (String) getAvailablePrincipal(principals);

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
        log.info("登录传入的username: {}", username);
        log.info("根据用户名查询数据库user信息：{}", user);
        log.info("从数据库中获取的password：{}", user.getPassword());
        log.info("从数据库中查询出先前随机生成盐字符串：{}", user.getSalt());
        //为了防止各个用户初始密码一致导致泄密，而相同的密码不同的盐加密后的结果也不同
        log.info("对取出的盐进行编码：{}", ByteSource.Util.bytes(user.getSalt()));
        log.info("当前shiro名称：{}", getName());

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getName(),             //用户名
                user.getPassword(),         //从数据库查询出来的安全密码
                ByteSource.Util.bytes(user.getSalt()),
                getName());                 //realm name
        return authenticationInfo;
    }
}
