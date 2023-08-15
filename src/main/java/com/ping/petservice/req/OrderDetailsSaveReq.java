package com.ping.petservice.req;

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
public class OrderDetailsSaveReq {


    private  Integer id;
    /**
     * 订单编号
     */
    private String oid;

    /**
     * 商品编号
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
