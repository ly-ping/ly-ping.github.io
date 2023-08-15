package com.ping.petservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ping.petservice.entity.OrderDetails;
import com.ping.petservice.req.OrderDetailsReq;
import com.ping.petservice.req.OrderDetailsSaveReq;
import com.ping.petservice.resp.PageResp;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
public interface IOrderDetailsService extends IService<OrderDetails> {

    void save(OrderDetailsSaveReq req);

    PageResp<OrderDetails> getList(OrderDetailsReq orderDetailsReq);

    void delete(Long id);
}
