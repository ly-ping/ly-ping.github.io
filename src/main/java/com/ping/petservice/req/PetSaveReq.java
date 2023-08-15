package com.ping.petservice.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author：Ping
 * @Date：2023/4/12
 **/
@Data
public class PetSaveReq {

    private static final long serialVersionUID = 1L;

    /**
     * 宠物id
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    /**
     * 宠物名称
     */
    private String pname;

    /**
     * 宠物物种
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
    private Integer owner;
}
