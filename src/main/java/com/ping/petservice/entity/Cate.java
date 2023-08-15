package com.ping.petservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *      商品分类表
 * </p>
 *
 * @author ping
 * @since 2023-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Cate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String catename;

    /**
     * 分类层级
     */
    private String catelevel;

    /**
     * 父分类id
     */
    private String catepid;


}
