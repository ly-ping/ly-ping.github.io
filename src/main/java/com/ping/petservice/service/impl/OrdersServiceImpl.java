package com.ping.petservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ping.petservice.entity.Orders;
import com.ping.petservice.mapper.OrdersMapper;
import com.ping.petservice.req.OrdersReq;
import com.ping.petservice.req.OrdersSaveReq;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IOrdersService;
import com.ping.petservice.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单，预约 服务实现类
 * </p>
 *
 * @author ping
 * @since 2023-04-12
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper,Orders> implements IOrdersService {


    @Resource
    OrdersMapper ordersMapper;

    //    查询
    @Override
    public PageResp<Orders> getList(OrdersReq ordersReq) {


        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();

//        模糊查询
        if (!ObjectUtils.isEmpty(ordersReq.getOid())) {//若Oid(订单编号)不为空
            queryWrapper.lambda().like(Orders::getOid, ordersReq.getOid());
        }
        if (!ObjectUtils.isEmpty(ordersReq.getUid())) {//若Uid(用户编号)不为空
            queryWrapper.lambda().like(Orders::getUid, ordersReq.getUid());
        }
        if (!ObjectUtils.isEmpty(ordersReq.getStatus())) {//若status(状态)不为空
            queryWrapper.lambda().like(Orders::getStatus, ordersReq.getStatus());
        }


//        分页
        Page<Orders> page = new Page<>(ordersReq.getCurrentPage(), ordersReq.getPageSize());//前端传来的当前页和总条数放到page
        IPage<Orders> ordersIPage = ordersMapper.selectPage(page, queryWrapper);//
//        将得到的total值塞进PageResp
        PageResp<Orders> pageResp = new PageResp<>();//转换，定义一个空的方法
//        将total和list值放进这个空方法
        pageResp.setTotal(ordersIPage.getTotal());
        pageResp.setList(ordersIPage.getRecords());
//        将这个值返回给前端，→service→controller
        return pageResp;
    }



    //    删除
    @Override
    public void delete(Long id) {
        QueryWrapper<Orders> wrapper = Wrappers.query();
        wrapper.eq("oid", id);
        ordersMapper.delete(wrapper);
    }

    //  增改
    @Override
    public void save(OrdersSaveReq req) {
        Orders orders = CopyUtil.copy(req, Orders.class);
//        判断：没有id就是新增方法，
        if (ObjectUtils.isEmpty(req.getId())){
            ordersMapper.insert(orders);
        }else {
//            判断：有id就是修改方法，
            ordersMapper.updateById(orders);
        }
    }
}
