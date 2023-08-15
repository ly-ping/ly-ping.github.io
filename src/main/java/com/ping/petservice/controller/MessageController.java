package com.ping.petservice.controller;


import com.ping.petservice.entity.Message;
import com.ping.petservice.req.MessageReq;
import com.ping.petservice.req.MessageSaveReq;
import com.ping.petservice.resp.CommonResp;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 留言表 前端控制器
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;


    //获取所有留言信息
    @GetMapping("/getall")
    public Object getAll(){
        return messageService.list();
    }

// 查询
    @GetMapping("/getlist")
    public CommonResp getList(MessageReq messageReq){

        CommonResp<PageResp<Message>> resp = new CommonResp<>();
        PageResp<Message> list = messageService.getList(messageReq);
        resp.setContent(list);

        return resp;
    }

    //回复（即修改）和发布（即新增）
    @PostMapping("/save")
    public CommonResp save(@RequestBody MessageSaveReq req){
        CommonResp<Message> resp = new CommonResp<>();
        messageService.save(req);
        return resp;

    }

//  删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Integer id){
        CommonResp<Message> resp = new CommonResp<>();
        messageService.delete(id);
        return resp;
    }


}
