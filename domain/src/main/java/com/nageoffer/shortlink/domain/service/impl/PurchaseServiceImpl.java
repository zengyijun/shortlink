package com.nageoffer.shortlink.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.domain.dao.entity.PurchaseDO;
import com.nageoffer.shortlink.domain.dao.mapper.PurchaseMapper;
import com.nageoffer.shortlink.domain.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.redisson.RedissonRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, PurchaseDO> implements PurchaseService {
    @Autowired
    private RedissonRateLimiter

}
