package com.nageoffer.shortlink.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.domain.dao.entity.AlipayDO;
import com.nageoffer.shortlink.domain.dto.req.PurchaseReqDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AlipayService extends IService<AlipayDO> {
    void buyDomain(PurchaseReqDTO requestParam, HttpServletResponse httpResponse);
    void buyVip(PurchaseReqDTO requestParam, HttpServletResponse httpResponse);
    boolean checkLogin(String username, String token);

    void verifyPurchase(HttpServletRequest requestParams);
}