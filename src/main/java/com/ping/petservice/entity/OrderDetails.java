package com.ping.petservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * 
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private  Integer id;
    /**
     * 订单编号 外键
     */
    private String oid;

    /**
     * 商品编号 外键
     */
    private Integer gid;
    /**
     * 商品名
     */
    private String gname;

    /**
     * 商品数量
     */
    private String quantity;

    /**
     * 每个商品的价格
     */
    private BigDecimal price;


}
