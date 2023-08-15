package com.ping.petservice.service;

import com.ping.petservice.entity.Cate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ping.petservice.req.CateReq;
import com.ping.petservice.req.CateSaveReq;
import com.ping.petservice.resp.PageResp;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ping
 * @since 2023-05-06
 */
public interface ICateService extends IService<Cate> {

    PageResp<Cate> getList(CateReq cateReq);

    void delete(Integer id);

    void save(CateSaveReq req);

}
