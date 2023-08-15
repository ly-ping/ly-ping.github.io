package com.ping.petservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ping.petservice.entity.Orders;
import com.ping.petservice.req.OrdersReq;
import com.ping.petservice.req.OrdersSaveReq;
import com.ping.petservice.resp.PageResp;

/**
 * <p>
 * 订单，预约 服务类
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
public interface IOrdersService extends IService<Orders> {

    PageResp<Orders> getList(OrdersReq ordersReq);

    void delete(Long id);

    void save(OrdersSaveReq req);
}
