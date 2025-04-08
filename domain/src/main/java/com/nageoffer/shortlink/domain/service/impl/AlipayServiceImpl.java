package com.nageoffer.shortlink.domain.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.domain.dao.entity.AlipayDO;
import com.nageoffer.shortlink.domain.dao.mapper.AlipayMapper;
import com.nageoffer.shortlink.domain.dto.req.AlipayReqDTO;
import com.nageoffer.shortlink.domain.dto.resp.AlipayRespDTO;
import com.nageoffer.shortlink.domain.service.AlipayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlipayServiceImpl extends ServiceImpl<AlipayMapper, AlipayDO> implements AlipayService {
    private final AlipayClient alipayClient;

    @Override
    public AlipayRespDTO payOrder(AlipayReqDTO requestParam){
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setOutTradeNo(requestParam.getOrderId());
        model.setTotalAmount(requestParam.getCost());
        model.setSubject(requestParam.getSubject());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        if(requestParam.getSubject().equals("域名购买")){
            model.setBody(requestParam.getDomain());
        }
        alipayTradePagePayRequest.setBizModel(model);


    }
}
