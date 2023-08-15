package com.ping.petservice.req;

import lombok.Data;

/**
 *
 * 视图对象，根据前端的要求改造一个实体类
 * @Author：Ping
 * @Date：2023/4/6
 *
 *
 **/
@Data
public class PetReq extends PageReq {

    private Integer pid;

    /**
     * 宠物名称
     */
    private String pname;

    /**
     * 宠物物种
     * 该属性来自breed表
     */
    private String breed;

    /**
     * 宠物性别
     */
    private String gender;

    /**
     * 宠物年龄
     */
    private String age;

    /**
     * 宠主id
     */
    private String owner;

}
