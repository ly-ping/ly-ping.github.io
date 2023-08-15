package com.ping.petservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品，服务
 * </p>
 *
 * @author ping
 * @since 2023-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品/服务id
     */
    @TableField(value = "gid")
    private Integer gid;

    /**
     * 商品/服务图片
     */

    private String image;

    /**
     * 商品/服务价格
     */
    private BigDecimal gprice;

    /**
     * 商品/服务名称
     */
    private String gname;

    /**
     * 商品/服务描述
     */
    private String gdetail;
    /**
     * 商品/服务类型 一级分类
     */

    private String category1;

    /**
     * 商品/服务类型 二级分类
     */

    private String category2;



}
