package com.ping.petservice.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *     前端获取的商品数据，新增商品所需要的数据,用于编辑和删除
 * 商品，服务
 * </p>
 *
 * @author ping
 * @since 2023-04-07
 */
@Data
public class GoodsSaveReq implements Serializable {
    /**
     * 商品/服务id
     */
//    @TableField(value = "gid")
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
     * 商品/服务类型
     */
    private String category1;

    /**
     * 商品/服务类型2
     */
    private String category2;

    /**
     * 商品/服务名称
     */
    private String gname;

    /**
     * 商品/服务描述
     */
    private String gdetail;


}
