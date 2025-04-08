package com.nageoffer.shortlink.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.domain.dao.entity.PurchaseDO;
import com.nageoffer.shortlink.domain.dto.req.PurchaseReqDTO;
import com.nageoffer.shortlink.domain.dto.resp.PurchaseRespDTO;

public interface PurchaseService extends IService<PurchaseDO> {

    PurchaseRespDTO buyDomain(PurchaseReqDTO requestParam);
    boolean checkLogin(String username, String token);


}
