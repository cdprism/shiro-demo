package org.shancm.shirodemo.bean;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.shancm.shirodemo.domain.entity.User;
import org.shancm.shirodemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Objects;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;



    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        /*//模拟找到用户信息
        Set<Role> roleSet = new HashSet<>(8);
        Set<Permission> permissionSet = new HashSet<>(8);
        Role role = new Role("1", "admin", permissionSet);
        roleSet.add(role);
        Permission permission = new Permission("1", "access");
        permissionSet.add(permission);

        //业务逻辑
        String username = (String) principalCollection.getPrimaryPrincipal();
        //模拟用用户名查找用户信息
        User user = new User("1", "Jerry", "123456", roleSet);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role userRole : user.getRoles()) {
            simpleAuthorizationInfo.addRole(userRole.getRoleName());

            for (Permission userRolePermission : userRole.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(userRolePermission.getPermissionsName());
            }
        }

        return simpleAuthorizationInfo;*/


        //获取当前登录对象
        Subject subject = SecurityUtils.getSubject();
        System.out.println("doGetAuthorizationInfo: "+subject.getPrincipals());
        User user = (User) subject.getPrincipal();

        //设置角色
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(user.getRoles());

        //设置权限
        for (String permission : user.getPermissions()) {
            info.addStringPermission(permission);
        }

        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (Objects.isNull(authenticationToken.getPrincipal())) {
            return null;
        }

        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        //模拟根据用户名查找用户信息
        User user = userService.getUserByName(name);
        if (Objects.isNull(user)) {
            //这里返回后会报出对应异常
            return null;
        }
        //这里验证authenticationToken和simpleAuthenticationInfo的信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), getName());
        return simpleAuthenticationInfo;

    }
}
