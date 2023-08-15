package com.ping.petservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * <p>
 * 订单，预约
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单编号
     */
    private String oid;

    /**
     * 下单用户 外键
     */
    private Integer uid;

    /**
     * 下单时间
     */
    private String ordertime;
    /**
     * 收货地址
     */
    private String address;

    /**
     * 订单状态（是否完成）
     */
    private String status;

    /**
     * 订单总价
     */
    private BigDecimal totalprice;


}
