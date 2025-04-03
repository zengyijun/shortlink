package com.nageoffer.shortlink.domain.dto.req;

import lombok.Data;

import java.util.Date;

@Data
public class PurchaseReqDTO {
//    查询用户是否登录
    private String token;
    private String username;
    private Long did;
    private String domain;
    private Date expire_time;
}
