package com.ping.petservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ping.petservice.entity.Pet;
import com.ping.petservice.req.PetReq;
import com.ping.petservice.req.PetSaveReq;
import com.ping.petservice.resp.PageResp;

/**
 * <p>
 * 宠物信息表 服务类
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
public interface IPetService extends IService<Pet> {

    void delete(Integer id);

    PageResp<Pet> getList(PetReq petReq);

    void save(PetSaveReq req);
}
