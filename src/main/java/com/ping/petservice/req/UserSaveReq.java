package com.ping.petservice.req;

import lombok.Data;

/**
 * <p>
 * 接收前端传来的注册信息
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@Data
public class UserSaveReq {

    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 账号
     */
    private Integer account;
    /**
     * 密码
     */
    private String password;

    /**
     * 用户名
     */
    private String screenname;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色
     */
    private String role;

    /**
     * 用户地址
     */
    private String address;


}
