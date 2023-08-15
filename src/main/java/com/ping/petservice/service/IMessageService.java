package com.ping.petservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ping.petservice.entity.Message;
import com.ping.petservice.req.MessageReq;
import com.ping.petservice.req.MessageSaveReq;
import com.ping.petservice.resp.PageResp;

/**
 * <p>
 * 留言表 服务类
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
public interface IMessageService extends IService<Message> {

    PageResp<Message> getList(MessageReq messageReq);

    void delete(Integer id);

    // 增 改
    void save(MessageSaveReq req);
}
