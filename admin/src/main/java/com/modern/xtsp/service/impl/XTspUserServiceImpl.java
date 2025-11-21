package com.modern.xtsp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.modern.tsp.domain.TspUser;
import com.modern.xtsp.mapper.XTspUserMapper;
import com.modern.xtsp.service.XTspUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@EnableCaching
@Slf4j
@Service
public class XTspUserServiceImpl extends ServiceImpl<XTspUserMapper, TspUser> implements XTspUserService {
}
