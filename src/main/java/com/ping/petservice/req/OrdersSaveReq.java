package com.ping.petservice.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Author：Ping
 * @Date：2023/4/12
 **/
@Data
public class OrdersSaveReq {


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
