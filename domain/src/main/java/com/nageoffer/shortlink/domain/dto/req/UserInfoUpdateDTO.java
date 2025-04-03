package com.nageoffer.shortlink.domain.dto.req;

import lombok.Data;

@Data
public class UserInfoUpdateDTO {
    private String token;
    private String username;
    private String old_domain;
    private String new_domain;
}
