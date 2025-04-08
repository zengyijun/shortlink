package com.nageoffer.shortlink.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.domain.common.convention.exception.ClientException;
import com.nageoffer.shortlink.domain.dao.entity.PurchaseDO;
import com.nageoffer.shortlink.domain.dao.mapper.PurchaseMapper;
import com.nageoffer.shortlink.domain.dto.req.PurchaseReqDTO;
import com.nageoffer.shortlink.domain.dto.resp.PurchaseRespDTO;
import com.nageoffer.shortlink.domain.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.nageoffer.shortlink.domain.common.constant.RedisCacheConstant.DOMAIN_PURCHASE_KEY;
import static com.nageoffer.shortlink.domain.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, PurchaseDO> implements PurchaseService {


    private final RBloomFilter<String> domainPurchaseBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
    private static final String Lua_script_path = "lua/domain_purchase_control.lua";

    @Override
    public PurchaseRespDTO buyDomain(PurchaseReqDTO requestParam){
        if(!checkLogin(requestParam.getUsername(), requestParam.getToken()))
            throw new ClientException("用户未登录！");
        RLock lock = redissonClient.getLock(DOMAIN_PURCHASE_KEY + requestParam.getDomain());
        if(!lock.tryLock()){
            throw new ClientException("域名已被购买");
        }
//        try{
//            Date purchaseTime = new Date();
////            int inserted = baseMapper.insert(BeanUtil.toBean());
//        }
        return null;
    }

    @Override
    public boolean checkLogin(String username, String token){
        return stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY+username, token) != null;
    }
    private boolean hasDomain(String domain){
        return domainPurchaseBloomFilter.contains(domain);
    }
}
