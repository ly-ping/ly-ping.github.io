package com.ping.petservice.req;


import lombok.Data;

import java.math.BigInteger;

/**
 * <p>
 * 用于查询，只保留查询所需要的参数
 * </p>
 *
 * @author ping
 * @since 2023-04-12
 *
 */
@Data
public class OrdersReq extends PageReq{



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
     * 订单状态（是否完成）
     */
    private String status;

}
