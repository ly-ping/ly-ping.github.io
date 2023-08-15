package com.ping.petservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ping.petservice.entity.Pet;
import com.ping.petservice.mapper.PetMapper;
import com.ping.petservice.req.PetReq;
import com.ping.petservice.req.PetSaveReq;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IPetService;
import com.ping.petservice.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 宠物信息表 服务实现类
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements IPetService {

    @Resource
    private PetMapper petMapper;

//    查询
    @Override
    public PageResp<Pet> getList(PetReq petReq) {
//          模糊查询
        QueryWrapper<Pet> queryWrapper = new QueryWrapper<>();
//
        if(!ObjectUtils.isEmpty(petReq.getPname())) {//若name不为空
            queryWrapper.lambda().like(Pet::getPname,petReq.getPname());
        }
        if(!ObjectUtils.isEmpty(petReq.getBreed())) {//若breed不为空
            queryWrapper.lambda().like(Pet::getBreed,petReq.getBreed());
        }
        if(!ObjectUtils.isEmpty(petReq.getOwner())) {//若owner不为空
            queryWrapper.lambda().like(Pet::getOwner,petReq.getOwner());
        }

        Page<Pet> page = new Page<>(petReq.getCurrentPage(),petReq.getPageSize());//前端传来的当前页和总条数放到page
        IPage<Pet> petIPage = this.petMapper.selectPage(page, queryWrapper);//得到总页数total
//        将得到的total值塞进PageResp
        PageResp<Pet> pageResp = new PageResp<>();//转换，定义一个空的方法
//        将total和list值放进这个空方法
        pageResp.setTotal(petIPage.getTotal());
        pageResp.setList(petIPage.getRecords());

////        将查询结果封装为List<Pet>对象
//        List<Pet> list = petMapper.selectList(queryWrapper);
//        //    二级查询，查出物种名和主人姓名
//        List<PetReq> result = new ArrayList<>();
////        遍历pet，将pet全都放到petReq
//        for (Pet pet1 : list) {
//            PetReq petReq1 = new PetReq();
////            将pet属性复制到petReq，使petReq除了breed和owner的属性都存在
//            BeanUtils.copyProperties(pet1,petReq1);
////            缺失的属性在对应表查询
//            QueryWrapper<Breed> breedQueryWrapper = new QueryWrapper<>();
//            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
////            将需要的属性赋值给对应的缺失属性
//            breedQueryWrapper.eq("id",pet1.getBreed());
//            userQueryWrapper.eq("uid",pet1.getOwner());
////
//            Breed breed = this.breedMapper.selectOne(breedQueryWrapper);
//            User user = this.userMapper.selectOne(userQueryWrapper);
////            属性赋给petReq
//            petReq1.setBreed(breed.getBreed());
//            petReq1.setOwner(user.getScreenname());
////            装载到集合中
//            result.add(petReq1);
//        }
        return pageResp;
    }

    //    删除
    @Override
    public void delete(Integer id) {
        QueryWrapper<Pet> wrapper = Wrappers.query();
        wrapper.eq("pid", id);
        petMapper.delete(wrapper);
    }


    //    增 改
    @Override
    public void save(PetSaveReq req) {
        Pet pet = CopyUtil.copy(req, Pet.class);
//        判断：没有id就是新增方法，
        if (ObjectUtils.isEmpty(req.getPid())){
            petMapper.insert(pet);
//         有id就是修改方法
        }else {
            UpdateWrapper<Pet> wrapper = new UpdateWrapper<>();
            wrapper.eq("pid", req.getPid());
            petMapper.update(pet, wrapper);
//            理由同删除方法
//            PetMapper.updateById(Pet);
        }
    }
}
