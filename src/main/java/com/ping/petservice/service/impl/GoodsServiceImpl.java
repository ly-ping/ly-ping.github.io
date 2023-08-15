package com.ping.petservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ping.petservice.entity.Goods;
import com.ping.petservice.mapper.GoodsMapper;
import com.ping.petservice.req.GoodsReq;
import com.ping.petservice.req.GoodsSaveReq;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IGoodsService;
import com.ping.petservice.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 商品，服务 服务实现类
 * </p>
 *
 * @author ping
 * @since 2023-04-07
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Resource
    private GoodsMapper goodsMapper;


    @Override
    public PageResp<Goods> getList(GoodsReq goodsReq) {

        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        模糊查询(单纯的条件查询可以把like改成eq)
        if(!ObjectUtils.isEmpty(goodsReq.getGid())) {//若id不为空
            queryWrapper.lambda().like(Goods::getGid,goodsReq.getGid());
        }
        if(!ObjectUtils.isEmpty(goodsReq.getCategory2())) {//若type不为空
            queryWrapper.lambda().like(Goods::getCategory2,goodsReq.getCategory2());
        }
        if(!ObjectUtils.isEmpty(goodsReq.getGname())) {//若name不为空
            queryWrapper.lambda().like(Goods::getGname,goodsReq.getGname());
        }

//        分页
        Page<Goods> page = new Page<>(goodsReq.getCurrentPage(), goodsReq.getPageSize());//前端传来的当前页和总条数放到page
        IPage<Goods> goodsIPage = goodsMapper.selectPage(page, queryWrapper);//得到总页数total
//        将得到的total值塞进PageResp
        PageResp<Goods> pageResp = new PageResp<>();//转换，定义一个空的方法
//        将total和list值放进这个空方法
        pageResp.setTotal(goodsIPage.getTotal());
        pageResp.setList(goodsIPage.getRecords());
//        将这个值返回给前端，→service→controller
        return pageResp;
    }

//    删除方法
    @Override
    public void delete(Integer id) {
        QueryWrapper<Goods> wrapper = Wrappers.query();
        wrapper.eq("gid", id);
        goodsMapper.delete(wrapper);
//        deleteById只能识别名为id的属性，若是属性名就叫id，使用下面这句就行，若不是，就使用上面句子
//        goodsMapper.deleteById(id);
    }


//    获取单个商品信息方法
    @Override
    public Goods detail(GoodsReq goodsReq) {
//        创建构造方法
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        判断前端是否传来参数，有就增加这个构造条件
        if(!ObjectUtils.isEmpty(goodsReq.getGid())) {//若id不为空
            queryWrapper.lambda().eq(Goods::getGid,goodsReq.getGid());
        }

        return goodsMapper.selectOne(queryWrapper);
    }

//    增 改
    @Override
    public void save(GoodsSaveReq req) {
        Goods goods = CopyUtil.copy(req, Goods.class);
//        判断：有id就是新增方法，
        if (ObjectUtils.isEmpty(req.getGid())){
            goodsMapper.insert(goods);
//         没有id就是修改方法
        }else {
            UpdateWrapper<Goods> wrapper = new UpdateWrapper<>();
            wrapper.eq("gid", req.getGid());
            goodsMapper.update(goods, wrapper);
//            理由同删除方法
//            goodsMapper.updateById(goods);
        }
    }



}
