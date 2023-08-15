package com.ping.petservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ping
 * @since 2023-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Breed implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物种编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 物种名
     */
    private String breed;


}
