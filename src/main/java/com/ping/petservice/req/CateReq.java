package com.ping.petservice.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *      商品分类表
 * </p>
 *
 * @author ping
 * @since 2023-05-06
 */
@Data
public class CateReq extends PageReq {


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
