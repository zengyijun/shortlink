package com.nageoffer.shortlink.domain.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.domain.common.convention.exception.ClientException;
import com.nageoffer.shortlink.domain.common.convention.exception.RemoteException;
import com.nageoffer.shortlink.domain.common.convention.exception.ServiceException;
import com.nageoffer.shortlink.domain.config.AlipayConfig;
import com.nageoffer.shortlink.domain.dao.entity.AlipayDO;
import com.nageoffer.shortlink.domain.dao.mapper.AlipayMapper;
import com.nageoffer.shortlink.domain.dto.req.PurchaseReqDTO;
import com.nageoffer.shortlink.domain.service.AlipayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.ServerException;
import java.util.Collections;

import static com.nageoffer.shortlink.domain.common.constant.RedisCacheConstant.*;


@Service
@RequiredArgsConstructor
public class AlipayServiceImpl extends ServiceImpl<AlipayMapper, AlipayDO> implements AlipayService {

    // 采用令牌桶对整体的购买服务进行限流，采用分布式锁对单个域名的购买进行限流
    private static final int BUCKET_CAPACITY = 50;
    private static final int REFILL_RATE = 5;
    private static final String PURCHASE_FLOW_CONTROL_LUA_PATH = "lua/token_bucket.lua";

    private final AlipayConfig alipayConfig;
    private final AlipayClient alipayClient;
    private final RBloomFilter<String> domainPurchaseBloomFilter;
    private final RBloomFilter<String> vipUserBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void buyDomain(PurchaseReqDTO requestParam, HttpServletResponse httpResponse) {
        if (!checkLogin(requestParam.getUsername(), requestParam.getToken())) {
            throw new ClientException("用户未登录！");
        }
//        令牌桶
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(PURCHASE_FLOW_CONTROL_LUA_PATH)));
        redisScript.setResultType(Long.class);
        long result;
        try{
            result = stringRedisTemplate.execute(redisScript, Collections.singletonList(PURCHASE_RATE_LIMIT_KEY+requestParam.getToken()),
                    String.valueOf(System.currentTimeMillis() / 1000),
                    String.valueOf(BUCKET_CAPACITY),
                    String.valueOf(REFILL_RATE));
        } catch (Throwable ex) {
            log.error("执行用户购买lua脚本出错");
            throw new RemoteException("内部错误");
        }
        if(result == 0){
            throw new ClientException("请求过于频繁，请稍后再试");
        }
// 获取锁前读数据库，看有没有相关订单
        LambdaQueryWrapper<AlipayDO> queryWrapper = Wrappers.lambdaQuery(AlipayDO.class)
                .eq(AlipayDO::getUsername, requestParam.getUsername())
                .eq(AlipayDO::getSubject, "域名购买")
                .eq(AlipayDO::getDomain, requestParam.getDomain());


        RLock lock = redissonClient.getLock(DOMAIN_PURCHASE_KEY + requestParam.getDomain());
        if (!lock.tryLock()) {
            throw new ClientException("域名已被购买");
        }
        if(domainPurchaseBloomFilter.contains(requestParam.getDomain())){
            throw new ClientException("域名已被购买");
        }
        try {
            AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
            AlipayTradePayModel model = new AlipayTradePayModel();
//            设置订单编号：当前时间时间戳
            long timestamp = System.currentTimeMillis();
            int rondom = (int) (Math.random() * 10000);
            long orderId = timestamp * 10000 + rondom;
            AlipayDO payment = new AlipayDO();
            payment.setOrderId(orderId);
            payment.setCost(requestParam.getCost());
            payment.setSubject("域名购买");
            payment.setDomain(requestParam.getDomain());
            model.setOutTradeNo(String.valueOf(orderId));
            model.setTotalAmount(String.valueOf(requestParam.getCost()));
            model.setSubject("域名购买"+requestParam.getDomain());
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            alipayTradePagePayRequest.setBizModel(model);
            String notify_url = "http://www.juniquan.com";
            alipayTradePagePayRequest.setReturnUrl(notify_url);
            String form = "";
            
            try {
                form = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
                baseMapper.insert(BeanUtil.toBean(payment, AlipayDO.class));
                httpResponse.setContentType("text/html;charset=utf-8");
                httpResponse.getWriter().write(form);
                httpResponse.getWriter().flush();
                httpResponse.getWriter().close();
            }catch(Exception e){
                throw new ServerException(e.getMessage());
            }

        }catch (DuplicateKeyException | ServerException ex) {
            throw new ClientException("内部错误，暂时无法购买");
        }
        finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void buyVip(PurchaseReqDTO requestParam, HttpServletResponse httpResponse){
        if (!checkLogin(requestParam.getUsername(), requestParam.getToken())) {
            throw new ClientException("用户未登录！");
        }
//                令牌桶
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(PURCHASE_FLOW_CONTROL_LUA_PATH)));
        redisScript.setResultType(Long.class);
        long result;
        try{
            result = stringRedisTemplate.execute(redisScript, Collections.singletonList(PURCHASE_RATE_LIMIT_KEY+requestParam.getToken()),
                    String.valueOf(System.currentTimeMillis() / 1000),
                    String.valueOf(BUCKET_CAPACITY),
                    String.valueOf(REFILL_RATE));
        } catch (Throwable ex) {
            log.error("执行用户购买lua脚本出错");
            throw new RemoteException("内部错误");
        }
        if(result == 0){
            throw new ClientException("请求过于频繁，请稍后再试");
        }

        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        AlipayTradePayModel model = new AlipayTradePayModel();
//           设置订单编号：当前时间时间戳
        long timestamp = System.currentTimeMillis();
        int rondom = (int) (Math.random() * 10000);
        long orderId = timestamp * 10000 + rondom;
        AlipayDO payment = new AlipayDO();
        payment.setOrderId(orderId);
        payment.setSubject("会员购买");
        payment.setCost(requestParam.getCost());
        model.setOutTradeNo(String.valueOf(orderId));
        model.setTotalAmount(String.valueOf(requestParam.getCost()));
        model.setSubject("域名购买" + requestParam.getDomain());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        alipayTradePagePayRequest.setBizModel(model);
        alipayTradePagePayRequest.setReturnUrl(alipayConfig.getNotifyUrl());
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
            baseMapper.insert(BeanUtil.toBean(payment, AlipayDO.class));
            httpResponse.setContentType("text/html;charset=utf-8");
            httpResponse.getWriter().write(form);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY + username, token) != null;
    }

    @Override
    public void verifyPurchase(HttpServletRequest requestParams){
        if(!requestParams.getParameter("trade_status").equals("TRADE_SUCCESS")){
            throw new ClientException("订单支付失败！");
        }
//      订单支付成功，为用户授予相应的权限

    }
}