package com.nageoffer.shortlink.domain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.domain.common.convention.result.Result;
import com.nageoffer.shortlink.domain.common.convention.result.Results;
import com.nageoffer.shortlink.domain.dto.req.DomainReqDTO;
import com.nageoffer.shortlink.domain.dto.req.DomainUploadDTO;
import com.nageoffer.shortlink.domain.dto.resp.DomainRespDTO;
import com.nageoffer.shortlink.domain.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DomainController {
    private final DomainService domainService;

    @GetMapping("/api/short-link/domain/v1/getdomain")
    Result<IPage<DomainRespDTO>> getDomain(DomainReqDTO requestParam){
        return Results.success(domainService.listAllDomain(requestParam));
    }
//    为管理员提供链接上传服务
    @PostMapping("/api/short-link/domain/v1/uploaddomain")
    Result<Void> uploadDomain(@RequestBody DomainUploadDTO requestParam){
        domainService.uploadDomain(requestParam);
        return Results.success();
    }
}
