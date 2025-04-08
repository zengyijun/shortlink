package com.nageoffer.shortlink.domain.controller;

import com.nageoffer.shortlink.domain.common.convention.result.Result;
import com.nageoffer.shortlink.domain.common.convention.result.Results;
import com.nageoffer.shortlink.domain.dto.req.AlipayReqDTO;
import com.nageoffer.shortlink.domain.dto.resp.AlipayRespDTO;
import com.nageoffer.shortlink.domain.service.AlipayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlipayController {

    private final AlipayService alipayService;

    @GetMapping("alipay/pay")
    public Result<AlipayRespDTO> pay(@RequestBody AlipayReqDTO requestparam){
        return Results(alipayService.payOrder(requestparam));
    }
//    @PostMapping("alipay/notify")


}
