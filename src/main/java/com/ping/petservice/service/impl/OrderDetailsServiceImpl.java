package com.ping.petservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ping.petservice.entity.OrderDetails;
import com.ping.petservice.mapper.OrderDetailsMapper;
import com.ping.petservice.req.OrderDetailsReq;
import com.ping.petservice.req.OrderDetailsSaveReq;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IOrderDetailsService;
import com.ping.petservice.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@Service
public class OrderDetailsServiceImpl extends ServiceImpl<OrderDetailsMapper, OrderDetails> implements IOrderDetailsService {

    @Resource
    OrderDetailsMapper orderDetailsMapper;

    @Override
    public void save(OrderDetailsSaveReq req) {
//        新增方法
        OrderDetails orderDetails = CopyUtil.copy(req, OrderDetails.class);
        orderDetailsMapper.insert(orderDetails);
    }

    //    删除方法
    @Override
    public void delete(Long id) {
        QueryWrapper<OrderDetails> wrapper = Wrappers.query();
        wrapper.eq("oid", id);
        orderDetailsMapper.delete(wrapper);
    }

    @Override
    public PageResp<OrderDetails> getList(OrderDetailsReq orderDetailsReq) {
        QueryWrapper<OrderDetails> queryWrapper = new QueryWrapper<>();

//        模糊查询
        if (!ObjectUtils.isEmpty(orderDetailsReq.getOid())) {//若Oid(订单编号)不为空
            queryWrapper.lambda().like(OrderDetails::getOid, orderDetailsReq.getOid());
        }
//        分页
        Page<OrderDetails> page = new Page<>(orderDetailsReq.getCurrentPage(), orderDetailsReq.getPageSize());//前端传来的当前页和总条数放到page
        IPage<OrderDetails> orderDetailsIPage = orderDetailsMapper.selectPage(page, queryWrapper);//
//        将得到的total值塞进PageResp
        PageResp<OrderDetails> pageResp = new PageResp<>();//转换，定义一个空的方法
//        将total和list值放进这个空方法
        pageResp.setTotal(orderDetailsIPage.getTotal());
        pageResp.setList(orderDetailsIPage.getRecords());
//        将这个值返回给前端，→service→controller
        return pageResp; 
    }


}
