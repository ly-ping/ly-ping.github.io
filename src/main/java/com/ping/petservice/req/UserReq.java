package com.ping.petservice.req;

import lombok.Data;

/**
 * <p>
 *  请求参数
 *  即 userVO，接收前端传来的数据
 *
 *  前端传参的接受,防止脏数据
 *
 *  登录
 * </p>
 *
 * @author ping
 * @since 2023-03-29
 */
@Data
public class UserReq extends PageReq {
    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 用户登录账号
     */
    private Integer account;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    private String role;
    /**
     * 用户查询：账号account，电话phone，昵称screenname
     */
    private String phone;


    private String screenname;

}
