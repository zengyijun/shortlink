package com.nageoffer.shortlink.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.domain.dao.entity.UserInfoDO;
import com.nageoffer.shortlink.domain.dto.req.UserInfoCreateDTO;
import com.nageoffer.shortlink.domain.dto.req.UserInfoReqDTO;
import com.nageoffer.shortlink.domain.dto.req.UserInfoUpdateDTO;
import com.nageoffer.shortlink.domain.dto.resp.UserInfoRespDTO;

/**
 *用户信息接口层
 */
public interface UserInfoService extends IService<UserInfoDO> {
    /**
     * 创建用户所拥有的domain信息
     * @param requestParam 创建用户信息
     */
    void createUserInfo(UserInfoCreateDTO requestParam);

    void update(UserInfoUpdateDTO requestParam);

    boolean checkLogin(String username, String token);

    IPage<UserInfoRespDTO> getDomain(UserInfoReqDTO requestParam);
}

