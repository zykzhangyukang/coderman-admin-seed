package com.company.project.service;
import com.company.project.dto.UserDTO;
import com.company.project.model.User;
import com.company.project.core.Service;


/**
 * Created by CodeGenerator on 2020/05/26.
 */
public interface UserService extends Service<User> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 用户登入
     * @param userDTO
     * @return
     */
    String login(UserDTO userDTO);
}
