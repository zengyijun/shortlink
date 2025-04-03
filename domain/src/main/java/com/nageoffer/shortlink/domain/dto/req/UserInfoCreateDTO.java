package com.nageoffer.shortlink.domain.dto.req;


import lombok.Data;

/**
 * 用户信息创建请求对象
 */
@Data
public class UserInfoCreateDTO {
    private String token;
    private String username;
    private String domain;
}
