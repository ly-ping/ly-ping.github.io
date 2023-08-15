package com.ping.petservice.req;

import lombok.Data;

/**
 *  从前端获取参数值
 *   使用@Data注释就可以不用写getter setter 和 toString
 * @Author：Ping
 * @Date：2023/4/6
 **/
@Data
public class PageReq {

    //    当前页数
    private Long currentPage;

    //    每页条数
    private Long pageSize;


}
