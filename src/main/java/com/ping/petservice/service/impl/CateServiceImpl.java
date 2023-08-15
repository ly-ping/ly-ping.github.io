package com.ping.petservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ping.petservice.entity.Cate;
import com.ping.petservice.mapper.CateMapper;
import com.ping.petservice.req.CateReq;
import com.ping.petservice.req.CateSaveReq;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.ICateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ping.petservice.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ping
 * @since 2023-05-06
 */
@Service
public class CateServiceImpl extends ServiceImpl<CateMapper, Cate> implements ICateService {

    @Resource
    private CateMapper cateMapper;
    
    @Override
    public PageResp<Cate> getList(CateReq cateReq) {
        QueryWrapper<Cate> queryWrapper = new QueryWrapper<>();
//        模糊查询(单纯的条件查询可以把like改成eq)
        if(!ObjectUtils.isEmpty(cateReq.getId())) {//若id不为空
            queryWrapper.lambda().like(Cate::getId,cateReq.getId());
        }
//        分页
        Page<Cate> page = new Page<>(cateReq.getCurrentPage(), cateReq.getPageSize());//前端传来的当前页和总条数放到page
        IPage<Cate> cateIPage = cateMapper.selectPage(page, queryWrapper);//得到总页数total
//        将得到的total值塞进PageResp
        PageResp<Cate> pageResp = new PageResp<>();//转换，定义一个空的方法
//        将total和list值放进这个空方法
        pageResp.setTotal(cateIPage.getTotal());
        pageResp.setList(cateIPage.getRecords());
//        将这个值返回给前端，→service→controller
        return pageResp;
    }




//  删除
    @Override
    public void delete(Integer id) {
        cateMapper.deleteById(id);
    }
//  增 改
    @Override
    public void save(CateSaveReq req) {
        Cate cate = CopyUtil.copy(req, Cate.class);
//        判断：有id就是新增方法，
        if (ObjectUtils.isEmpty(req.getId())){
            cateMapper.insert(cate);
//         没有id就是修改方法
        }else {
            cateMapper.updateById(cate);
        }
    }
}
