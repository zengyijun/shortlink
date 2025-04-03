package com.nageoffer.shortlink.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.domain.dao.entity.DomainInfoDO;
import com.nageoffer.shortlink.domain.dto.req.DomainReqDTO;
import com.nageoffer.shortlink.domain.dto.req.DomainUploadDTO;
import com.nageoffer.shortlink.domain.dto.resp.DomainRespDTO;

public interface DomainService extends IService<DomainInfoDO> {
    IPage<DomainRespDTO> listAllDomain(DomainReqDTO requestParam);

    void uploadDomain(DomainUploadDTO requestParam);
}
