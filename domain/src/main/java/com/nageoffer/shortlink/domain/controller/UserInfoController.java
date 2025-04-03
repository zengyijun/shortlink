package com.nageoffer.shortlink.domain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.domain.common.convention.result.Result;
import com.nageoffer.shortlink.domain.common.convention.result.Results;
import com.nageoffer.shortlink.domain.dto.req.UserInfoCreateDTO;
import com.nageoffer.shortlink.domain.dto.req.UserInfoReqDTO;
import com.nageoffer.shortlink.domain.dto.req.UserInfoUpdateDTO;
import com.nageoffer.shortlink.domain.dto.resp.UserInfoRespDTO;
import com.nageoffer.shortlink.domain.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
* 用户信息控制层
* */
@RestController
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;
    /**
     * 创建用户
     */
    @PostMapping("/api/short-link/domain/v1/createinfo")
    public Result<Void> createUserInfo(@RequestBody UserInfoCreateDTO requestParam){
        userInfoService.createUserInfo(requestParam);
        return Results.success();
    }
    /**
     * 修改信息
     */
    @PutMapping("/api/short-link/domain/v1/modifyinfo")
    public Result<Void> update(@RequestBody UserInfoUpdateDTO requestParam){
        userInfoService.update(requestParam);
        return Results.success();
    }
    /**
     * 查询拥有的domain
     * */
    @GetMapping("/api/short-link/domain/v1/getinfo")
    public Result<IPage<UserInfoRespDTO>> getDomain(@RequestBody UserInfoReqDTO requestParam){
        return Results.success(userInfoService.getDomain(requestParam));
    }
}
