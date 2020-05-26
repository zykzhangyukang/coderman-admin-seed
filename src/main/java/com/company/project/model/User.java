package com.company.project.model;

import javax.persistence.*;

@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 0: 女,1 :男
     */
    private Integer sex;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 角色名
     */
    private String role;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取0: 女,1 :男
     *
     * @return sex - 0: 女,1 :男
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置0: 女,1 :男
     *
     * @param sex 0: 女,1 :男
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取电话号码
     *
     * @return phone - 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话号码
     *
     * @param phone 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取角色名
     *
     * @return role - 角色名
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置角色名
     *
     * @param role 角色名
     */
    public void setRole(String role) {
        this.role = role;
    }
}