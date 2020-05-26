package com.company.project.realm;

import com.company.project.configurer.JWTToken;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.company.project.utils.JWTUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("用户授权---");
        return null;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        // 解密获得username，用于和数据库进行对比
        String token = (String) auth.getCredentials();
        String username = JWTUtils.getUsername(token);

        if (username == null) {
            throw new AuthenticationException(" token错误，请重新登入！");
        }

        User userBean = userService.findUserByUsername(username);

        if (userBean == null) {
            throw new AccountException("账号不存在!");
        }
        if(JWTUtils.isExpire(token)){
            throw new AuthenticationException(" token过期，请重新登入！");
        }

        if (! JWTUtils.verify(token, username, userBean.getPassword())) {
            throw new CredentialsException("密码错误!");
        }

        return new SimpleAuthenticationInfo(userBean, token, getName());
    }
}
