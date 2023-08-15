package com.ping.petservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ping.petservice.entity.Message;
import com.ping.petservice.mapper.MessageMapper;
import com.ping.petservice.req.MessageReq;
import com.ping.petservice.req.MessageSaveReq;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IMessageService;
import com.ping.petservice.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 留言表 服务实现类
 * </p>
 *
 * @author ping
 * @since 2023-03-20
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Resource
    MessageMapper messageMapper;


    @Override
    public PageResp<Message> getList(MessageReq messageReq) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
//        模糊查询(单纯的条件查询可以把like改成eq)
        if (!ObjectUtils.isEmpty(messageReq.getUid())) {//若uid不为空
            queryWrapper.lambda().like(Message::getUid, messageReq.getUid());
        }


//        分页
        Page<Message> page = new Page<>(messageReq.getCurrentPage(), messageReq.getPageSize());//前端传来的当前页和总条数放到page
        IPage<Message> messageIPage = messageMapper.selectPage(page, queryWrapper);//得到总页数total
//        将得到的total值塞进PageResp
        PageResp<Message> pageResp = new PageResp<>();//转换，定义一个空的方法
//        将total和list值放进这个空方法
        pageResp.setTotal(messageIPage.getTotal());
        pageResp.setList(messageIPage.getRecords());
//        将这个值返回给前端，→service→controller
        return pageResp;
    }

//    删除
    @Override
    public void delete(Integer id) {
        messageMapper.deleteById(id);
    }
    
    
    // 增 改
    @Override
    public void save(MessageSaveReq req) {
        Message message = CopyUtil.copy(req, Message.class);
//        判断：有id就是新增方法，
        if (ObjectUtils.isEmpty(req.getId())){
            messageMapper.insert(message);
//         没有id就是修改方法
        }else {
            messageMapper.updateById(message);
        }
    }

}


