package com.ping.petservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ping.petservice.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 *
 * </p>
 *
 * @author ping
 * @since 2023-03-29
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
