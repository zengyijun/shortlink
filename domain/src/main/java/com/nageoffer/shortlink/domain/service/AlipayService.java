package com.nageoffer.shortlink.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.domain.dao.entity.AlipayDO;
import com.nageoffer.shortlink.domain.dto.req.AlipayReqDTO;
import com.nageoffer.shortlink.domain.dto.resp.AlipayRespDTO;

public interface AlipayService extends IService<AlipayDO> {
    AlipayRespDTO payOrder(AlipayReqDTO requestparam);
}
