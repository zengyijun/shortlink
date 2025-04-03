package com.nageoffer.shortlink.domain.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nageoffer.shortlink.domain.dao.entity.DomainInfoDO;
import lombok.Data;

@Data
public class DomainReqDTO extends Page<DomainInfoDO> {

    private String token;
    private String username;
}
