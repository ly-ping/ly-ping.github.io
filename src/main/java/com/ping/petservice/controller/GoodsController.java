package com.ping.petservice.controller;


import com.ping.petservice.entity.Goods;
import com.ping.petservice.req.GoodsReq;
import com.ping.petservice.req.GoodsSaveReq;
import com.ping.petservice.resp.CommonResp;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IGoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 商品，服务 前端控制器
 * </p>
 *
 * @author ping
 * @since 2023-04-07
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private IGoodsService goodsService;

    //获取所有商品信息
    @GetMapping("/getall")
    public Object getAll() {
        return goodsService.list();
    }


    @GetMapping("/getlist")
    public CommonResp getList(GoodsReq goodsReq) {

        CommonResp<PageResp<Goods>> resp = new CommonResp<>();
        PageResp<Goods> list = goodsService.getList(goodsReq);
        resp.setContent(list);

        return resp;
    }


    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Integer id) {
        CommonResp<Goods> resp = new CommonResp<>();
        goodsService.delete(id);
        return resp;
    }

    @GetMapping("/detail/{id}")
    public CommonResp<Goods> detail(@PathVariable Integer id) {
        CommonResp<Goods> resp = new CommonResp<>();
        GoodsReq goodsReq = new GoodsReq();
        goodsReq.setGid(id);
        resp.setContent(goodsService.detail(goodsReq));
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody GoodsSaveReq req) {
        CommonResp<Goods> resp = new CommonResp<>();
        goodsService.save(req);
        return resp;

    }

}