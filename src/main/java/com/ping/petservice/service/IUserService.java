package com.ping.petservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ping.petservice.entity.User;
import com.ping.petservice.req.UserReq;
import com.ping.petservice.req.UserSaveReq;
import com.ping.petservice.resp.CommonResp;
import com.ping.petservice.resp.PageResp;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ping
 * @since 2023-03-29
 */
public interface IUserService extends IService<User> {
    UserReq login(UserReq req, CommonResp<UserReq> resp);

    UserSaveReq register(UserSaveReq req, CommonResp<UserSaveReq> resp);

    PageResp<User> getList(UserReq userReq);

    void delete(Integer id);

    void save(UserSaveReq req);
}
