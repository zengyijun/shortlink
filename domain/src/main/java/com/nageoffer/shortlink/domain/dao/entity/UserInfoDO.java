package com.nageoffer.shortlink.domain.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nageoffer.shortlink.domain.common.database.BaseDO;
import lombok.Data;

@Data
@TableName("t_user_info")
public class UserInfoDO extends BaseDO {
        private Long id;
        private String username;
        private String domain;
}
