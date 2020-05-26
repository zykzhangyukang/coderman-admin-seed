package com.company.project.service.impl;

import com.company.project.configurer.JWTToken;
import com.company.project.core.ServiceException;
import com.company.project.dao.UserMapper;
import com.company.project.dto.UserDTO;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.company.project.core.AbstractService;
import com.company.project.utils.JWTUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/05/26.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Resource
    private UserMapper tbUserMapper;

    /**
     * 根据用户查找用户信息
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        User t = new User();
        t.setUsername(username);
        return tbUserMapper.selectOne(t);
    }

    /**
     * 用户登入
     * @param userDTO
     * @return
     */
    @Override
    public String login(UserDTO userDTO) {
        String username = userDTO.getUsername();
        User dbUser = findUserByUsername(username);
        if(dbUser==null){
            throw new ServiceException("用户不存在");
        }
        String token = JWTUtils.sign(username, userDTO.getPassword());
        JWTToken jwtToken = new JWTToken(token);
        try {
            SecurityUtils.getSubject().login(jwtToken);
        } catch (AuthenticationException e) {
            throw new ServiceException(e.getMessage());
        }
        return token;
    }
}
