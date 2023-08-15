package com.ping.petservice.req;

import lombok.Data;

/**
 * <p>
 * 留言表
 * </p>
 *
 * @author ping
 * @since 2023-04-12
 */
@Data
public class MessageReq extends PageReq {

    /**
     * 留言编号
     */
    private Integer id;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 留言时间
     */
    private String addtime;

    /**
     * 留言用户id
     */
    private Integer uid;

    /**
     * 回复时间
     */
    private String replytime;

    /**
     * 回复内容
     */
    private String replycontent;
}