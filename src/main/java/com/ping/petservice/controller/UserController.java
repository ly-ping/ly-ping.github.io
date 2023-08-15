package com.ping.petservice.controller;


import com.ping.petservice.entity.User;
import com.ping.petservice.req.UserReq;
import com.ping.petservice.req.UserSaveReq;
import com.ping.petservice.resp.CommonResp;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ping
 * @since 2023-03-29
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;


//    登录
    @PostMapping("/login")
    public CommonResp login(@RequestBody UserReq req){
        //加密加盐
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserReq> resp = new CommonResp<>();
        UserReq userReq = userService.login(req,resp);
        resp.setContent(userReq);
        return resp;
    }

//    注册
    @PostMapping("/register")
    public CommonResp register(@RequestBody UserSaveReq req ){
        //加密加盐
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserSaveReq> resp = new CommonResp<>();
        UserSaveReq userSaveReq = userService.register(req,resp);
        resp.setContent(userSaveReq);
        return resp;
    }


    //查询所有用户信息
    @GetMapping("/getall")
    public Object getAll(){
        return userService.list();
    }



    //模糊查询+分页
    @GetMapping("/getlist")
    public CommonResp getList(UserReq userReq){

        CommonResp<PageResp<User>> resp = new CommonResp<>();
        PageResp<User> list = userService.getList(userReq);
        resp.setContent(list);

        return resp;
    }

    // 增 改
    @PostMapping("/save")
    public CommonResp save(@RequestBody UserSaveReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<User> resp = new CommonResp<>();
        userService.save(req);
        return resp;

    }
    // 删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Integer id){
        CommonResp<User> resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

}
