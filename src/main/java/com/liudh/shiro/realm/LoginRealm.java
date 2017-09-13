package com.liudh.shiro.realm;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liudh.shiro.pojo.UPermission;
import com.liudh.shiro.pojo.URole;
import com.liudh.shiro.pojo.UUser;
import com.liudh.shiro.service.PermissionService;
import com.liudh.shiro.service.RoleService;
import com.liudh.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by liudh on 2017/5/28.
 */
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
        UUser user = (UUser)SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getId();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        //根据用户ID查询角色（role），放入到Authorization里。
        Set<String> roleList = roleService.selectRoleByUserId(userId);
        info.setRoles(roleList);
        //根据用户ID查询权限（permission），放入到Authorization里。
        Set<String> permList = permissionService.selectPermissionByUserId(userId);
        info.setStringPermissions(permList);
        return info;
    }

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;

        Session session = getSession();
        String code = (String)session.getAttribute("100000");
//        if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
//            throw new AuthenticationException("msg:验证码错误, 请重试.");
//        }
        UUser user = userService.getUserByName(token.getUsername());
        if(user != null){
            if(user.getStatus() !=null && user.getStatus() == 0){
                throw new AuthenticationException("msg:该已帐号禁止登录.");
            }
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, user.getPswd(), this.getName());
            this.setSession("currentUser", user);
            return authcInfo;
        }
        return null;
    }

    /**
     * 保存登录名
     */
    private void setSession(Object key, Object value){
        Session session = getSession();
        System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
        if(null != session){
            session.setAttribute(key, value);
        }
    }

    private Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
        }catch (InvalidSessionException e){

        }
        return null;
    }
}
