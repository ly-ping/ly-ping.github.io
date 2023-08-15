package com.ping.petservice.req;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：Ping
 * @Date：2023/4/19
 **/
@Data
public class MessageSaveReq {
    /**
     * 留言编号
     */
    private Integer id;

    /**
     * 留言id
     */
    private Integer uid;
    /**
     * 留言内容
     */
    private String content;
    /**
     * 留言时间
     */
    private String addtime;
    /**
     * 回复时间
     */
    private String replytime;

    /**
     * 回复内容
     */
    private String replycontent;
}
