package com.nageoffer.shortlink.domain.controller;

import com.nageoffer.shortlink.domain.dto.req.AlipayReqDTO;
import com.nageoffer.shortlink.domain.service.AlipayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AlipayController {

    private final AlipayService alipayService;

    @GetMapping("alipay/pay")
    public void pay(@RequestBody AlipayReqDTO requestparam){
        alipayService.payOrder(requestparam);
    }
    @PostMapping("alipay/notify")
    public void notify(@RequestParam Object Params){

    }


}
