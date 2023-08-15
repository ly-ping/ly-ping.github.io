package com.ping.petservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.soap.SAAJResult;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 留言表
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 留言编号
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 留言内容
     */
    private String replycontent;

}
