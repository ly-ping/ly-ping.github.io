package com.ping.petservice.controller;


import com.ping.petservice.entity.Orders;
import com.ping.petservice.req.OrdersReq;
import com.ping.petservice.req.OrdersSaveReq;
import com.ping.petservice.resp.CommonResp;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单，预约 前端控制器
 * </p>
 *
 * @author ping
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private IOrdersService orderService;



    //查询所有订单信息
    @GetMapping("/getall")
    public Object getAll(){
        return orderService.list();
    }



    //    查询+分页
    @GetMapping("/getlist")
    public CommonResp getList(OrdersReq ordersReq){

        CommonResp<PageResp<Orders>> resp = new CommonResp<>();
        PageResp<Orders> list = orderService.getList(ordersReq);
        resp.setContent(list);
        return resp;
    }

//    删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp<Orders> resp = new CommonResp<>();
        orderService.delete(id);
        return resp;
    }


//    增 改
    @PostMapping("/save")
    public CommonResp save(@RequestBody OrdersSaveReq req){
        CommonResp<Orders> resp = new CommonResp<>();
        orderService.save(req);
        return resp;
    }
}
