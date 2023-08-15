package com.ping.petservice.controller;


import com.ping.petservice.entity.OrderDetails;
import com.ping.petservice.req.OrderDetailsReq;
import com.ping.petservice.req.OrderDetailsSaveReq;
import com.ping.petservice.resp.CommonResp;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@RestController
@RequestMapping("/orderdetail")
public class OrderDetailsController {

    @Autowired
    private IOrderDetailsService orderDetailsService;


    //查询所有订单信息
    @GetMapping("/getall")
    public Object getAll(){
        return orderDetailsService.list();
    }

//    查询+分页
    @GetMapping("/getlist")
    public CommonResp getList(OrderDetailsReq orderDetailsReq){

        CommonResp<PageResp<OrderDetails>> resp = new CommonResp<>();
        PageResp<OrderDetails> list = orderDetailsService.getList(orderDetailsReq);
        resp.setContent(list);
        return resp;
    }

//    新增
    @PostMapping("/save")
    public CommonResp save(@RequestBody OrderDetailsSaveReq req){
        CommonResp<OrderDetails> resp = new CommonResp<>();
        orderDetailsService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp<OrderDetails> resp = new CommonResp<>();
        orderDetailsService.delete(id);
        return resp;
    }
}
