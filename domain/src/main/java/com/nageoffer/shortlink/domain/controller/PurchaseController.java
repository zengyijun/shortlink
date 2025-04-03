package com.nageoffer.shortlink.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


// 控制器
@RestController
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseController purchaseController;

    @PutMapping("/api/short-link/domain/v1/purchase")
    public Result<PurchaseRespDTO> buyDomain(@RequestBody PurchaseReqDTO requestParam){
        return Results(purchaseController.buyDomain(requestParam));
    }
}
