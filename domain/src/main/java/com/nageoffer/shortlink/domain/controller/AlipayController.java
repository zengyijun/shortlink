package com.nageoffer.shortlink.domain.controller;

import com.nageoffer.shortlink.domain.common.convention.result.Result;
import com.nageoffer.shortlink.domain.common.convention.result.Results;
import com.nageoffer.shortlink.domain.dto.req.PurchaseReqDTO;
import com.nageoffer.shortlink.domain.service.AlipayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlipayController {

    private final AlipayService alipayService;



    @PostMapping("/api/short-link/domain/v1/purchase/notify")
    Result<Void> notify(HttpServletRequest requestParams) {
        alipayService.verifyPurchase(requestParams);
        return Results.success();
        // 支付回调逻辑
    }

    @GetMapping("/api/short-link/domain/v1/purchase/domain")
    Result<Void> buyDomain(@RequestBody PurchaseReqDTO requestParam, HttpServletResponse httpResponse) {
        alipayService.buyDomain(requestParam, httpResponse);
        return Results.success();
    }
    @GetMapping("/api/short-link/domain/v1/purchase/vip")
    Result<Void> buyDomain(@RequestBody PurchaseReqDTO requestParam, HttpServletResponse httpResponse) {
        alipayService.buyVip(requestParam, httpResponse);
        return Results.success();
    }
}