package com.ping.petservice.controller;


import com.ping.petservice.entity.Cate;
import com.ping.petservice.req.CateReq;
import com.ping.petservice.req.CateSaveReq;
import com.ping.petservice.resp.CommonResp;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.ICateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ping
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/cate")
public class CateController {
    @Resource
    private ICateService cateService;

    //获取所有商品信息
    @GetMapping("/getall")
    public Object getAll(){
        return cateService.list();
    }

    @GetMapping("/getlist")
    public CommonResp getList(CateReq cateReq){

        CommonResp<PageResp<Cate>> resp = new CommonResp<>();
        PageResp<Cate> list = cateService.getList(cateReq);
        resp.setContent(list);

        return resp;
    }


    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Integer id){
        CommonResp<Cate> resp = new CommonResp<>();
        cateService.delete(id);
        return resp;
    }


    @PostMapping("/save")
    public CommonResp save(@RequestBody CateSaveReq req){
        CommonResp<Cate> resp = new CommonResp<>();
        cateService.save(req);
        return resp;

    }
}
