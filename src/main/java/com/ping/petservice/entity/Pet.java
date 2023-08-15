package com.ping.petservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 宠物信息表
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pet implements Serializable {

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
