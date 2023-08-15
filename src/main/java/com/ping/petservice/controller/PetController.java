package com.ping.petservice.controller;


import com.ping.petservice.entity.Pet;
import com.ping.petservice.req.PetReq;
import com.ping.petservice.req.PetSaveReq;
import com.ping.petservice.resp.CommonResp;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IBreedService;
import com.ping.petservice.service.IPetService;
import com.ping.petservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 宠物信息表 前端控制器
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private IPetService petService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IBreedService breedService;

    //获取所有宠物信息
    @GetMapping("/getall")
    public Object getAll(){
        return petService.list();
    }

    // 增  改
    @PostMapping("/save")
    public CommonResp list(@RequestBody PetSaveReq req) {
        CommonResp<Pet> resp = new CommonResp<>();
        petService.save(req);
        return resp;
    }


    // 删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Integer id) {
        CommonResp<Pet> resp = new CommonResp<>();
        petService.delete(id);
        return resp;
    }

    // 模糊查询
    @GetMapping("/petlist")
    public CommonResp getList(PetReq petReq) {
        CommonResp<PageResp<Pet>> resp = new CommonResp<>();
        PageResp<Pet> list = petService.getList(petReq);
        resp.setContent(list);

        return resp;
    }
}
