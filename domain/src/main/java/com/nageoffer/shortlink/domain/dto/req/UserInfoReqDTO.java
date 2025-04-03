package com.nageoffer.shortlink.domain.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nageoffer.shortlink.domain.dao.entity.UserInfoDO;
import lombok.Data;

@Data
public class UserInfoReqDTO extends Page<UserInfoDO> {
    private String token;
    private String username;
}
