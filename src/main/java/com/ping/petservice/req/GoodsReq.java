package com.ping.petservice.req;

import lombok.Data;

/**
 * <p>
 * 商品，服务
 * 查询只需要对比这三个数据，所以只保留这三个参数，用于查询
 * </p>
 *
 * @author ping
 * @since 2023-04-07
 */
@Data
public class GoodsReq extends PageReq {

    /**
     * 商品/服务id
     */
    private Integer gid;

    /**
     * 商品/服务类型
     * 实体类改造，该属性来自GoodsType表
     */
    private String category2;

    /**
     * 商品/服务名称
     */
    private String gname;


}
