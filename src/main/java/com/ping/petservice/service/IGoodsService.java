package com.ping.petservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ping.petservice.entity.Goods;
import com.ping.petservice.req.GoodsReq;
import com.ping.petservice.req.GoodsSaveReq;
import com.ping.petservice.resp.PageResp;

/**
 * <p>
 * 商品，服务 服务类
 * </p>
 *
 * @author ping
 * @since 2023-04-07
 */
public interface IGoodsService extends IService<Goods> {


    PageResp<Goods> getList(GoodsReq goodsReq);

    //    删除方法
    void delete(Integer id);


    Goods detail(GoodsReq goodsReq);


    void save(GoodsSaveReq req);

}
