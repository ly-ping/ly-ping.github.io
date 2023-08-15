package com.ping.petservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ping.petservice.entity.Breed;
import com.ping.petservice.mapper.BreedMapper;
import com.ping.petservice.service.IBreedService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ping
 * @since 2023-04-06
 */
@Service
public class BreedServiceImpl extends ServiceImpl<BreedMapper, Breed> implements IBreedService {

}
