package com.nageoffer.shortlink.domain.controller;

import com.nageoffer.shortlink.domain.common.convention.result.Result;
import com.nageoffer.shortlink.domain.common.convention.result.Results;
import com.nageoffer.shortlink.domain.dto.req.PurchaseReqDTO;
import com.nageoffer.shortlink.domain.dto.resp.PurchaseRespDTO;
import com.nageoffer.shortlink.domain.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


// 控制器
@RestController
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/api/short-link/domain/v1/purchase/domain")
    public Result<PurchaseRespDTO> buyDomain(@RequestBody PurchaseReqDTO requestParam){
        return Results.success(purchaseService.buyDomain(requestParam));
    }

    @PostMapping("/api/short-link/domain/v1/purchase/notify")
    public void notify(@RequestParam Object params){

    }

}
