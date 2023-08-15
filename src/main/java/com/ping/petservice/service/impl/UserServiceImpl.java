package com.ping.petservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ping.petservice.entity.User;
import com.ping.petservice.exception.BusinessExceptionCode;
import com.ping.petservice.mapper.UserMapper;
import com.ping.petservice.req.UserReq;
import com.ping.petservice.req.UserSaveReq;
import com.ping.petservice.resp.CommonResp;
import com.ping.petservice.resp.PageResp;
import com.ping.petservice.service.IUserService;
import com.ping.petservice.utils.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ping
 * @since 2023-03-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource//作用等同@Autowired
    private UserMapper userMapper;


//    登录方法
    @Override
    public UserReq login(UserReq req, CommonResp<UserReq> resp) {//req:前端传来的数据，resp：后台数据
//        查询账号是否存在
        User userDB = selectByUser(req.getAccount());
//          查询是否为空，为空提示用户不存在或密码错误
        if(ObjectUtils.isEmpty(userDB)){
            resp.setSuccess(false);
            resp.setMessage(BusinessExceptionCode.LOGIN_USER_ERROR.getDesc());//调用枚举
        }else {
//            再对比密码和身份是否正确,一致则登录成功
            if (userDB.getPassword().equals(req.getPassword())&&userDB.getRole().equals(req.getRole())){
                UserReq userReq = CopyUtil.copy(userDB, UserReq.class);
                return  userReq;
            }else {
//                不一致则提示登录失败
                resp.setSuccess(false);
                resp.setMessage(BusinessExceptionCode.LOGIN_USER_ERROR.getDesc());
            }
        }

        return req;
    }


//    注册方法
    @Override
    public UserSaveReq register(UserSaveReq req, CommonResp<UserSaveReq> resp) {
        User user = CopyUtil.copy(req,User.class);
//        先判断id是否为空
        if(ObjectUtils.isEmpty(req.getUid())){
//        查询账户是否存在
            User userDb = selectByUser(req.getAccount());
            if (ObjectUtils.isEmpty(userDb)){
//                若不存在则添加用户，注册成功。 注册用户默认身份为member
                user.setRole("member");
                userMapper.insert(user);
            }else {
//                若账号已存在,注册失败
                resp.setSuccess(false);
                resp.setMessage(BusinessExceptionCode.USER_LOGIN_NAME_EXIST.getDesc());
            }
        }
        return req;
    }



    //   查询数据库账号是否存在
    public User selectByUser(Integer account){
//        构造条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        将前端传来的账号和数据库查询的进行对比
        wrapper.lambda().eq(User::getAccount,account);
//        发送给mapper的方法获取返回结果
        List<User> userList= userMapper.selectList(wrapper);

//        如果返回结果为空，说明账号不存在
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }else {
//        若不为空，就把集合返回去
          return userList.get(0);
        }
    }


    @Override
    public PageResp<User> getList(UserReq userReq) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        模糊查询(单纯的条件查询可以把like改成eq)
        if(!ObjectUtils.isEmpty(userReq.getAccount())) {//若account不为空
            queryWrapper.lambda().like(User::getAccount,userReq.getAccount());
        }
        if(!ObjectUtils.isEmpty(userReq.getPhone())) {//若phone不为空
            queryWrapper.lambda().like(User::getPhone,userReq.getPhone());
        }
        if(!ObjectUtils.isEmpty(userReq.getScreenname())) {//若screenname不为空
            queryWrapper.lambda().like(User::getScreenname,userReq.getScreenname());
        }

//        分页
        Page<User> page = new Page<>(userReq.getCurrentPage(), userReq.getPageSize());//前端传来的当前页和总条数放到page
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);//得到总页数total
//        将得到的total值塞进PageResp
        PageResp<User> pageResp = new PageResp<>();//转换，定义一个空的方法
//        将total和list值放进这个空方法
        pageResp.setTotal(userIPage.getTotal());
        pageResp.setList(userIPage.getRecords());
//        将这个值返回给前端，→service→controller
        return pageResp;
    }
//  删除
    @Override
    public void delete(Integer id) {
        QueryWrapper<User> wrapper = Wrappers.query();
        wrapper.eq("uid", id);
        userMapper.delete(wrapper);
//        deleteById只能识别名为id的属性，若是属性名就叫id，使用下面这句就行，若不是，就使用上面句子
//        UserMapper.deleteById(id);
    }
//  增 改
    @Override
    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
//        判断：没有id就是新增方法，
        if (ObjectUtils.isEmpty(req.getUid())){
            userMapper.insert(user);
//         有id就是修改方法
        }else {
            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.eq("uid", req.getUid());
            userMapper.update(user, wrapper);
//            理由同删除方法
//            UserMapper.updateById(User);
        }
    }
}
