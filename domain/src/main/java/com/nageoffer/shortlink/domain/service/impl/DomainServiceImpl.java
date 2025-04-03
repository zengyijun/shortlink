package com.nageoffer.shortlink.domain.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.domain.common.convention.exception.ClientException;
import com.nageoffer.shortlink.domain.common.convention.exception.ServiceException;
import com.nageoffer.shortlink.domain.dao.entity.DomainInfoDO;
import com.nageoffer.shortlink.domain.dao.mapper.DomainInfoMapper;
import com.nageoffer.shortlink.domain.dto.req.DomainReqDTO;
import com.nageoffer.shortlink.domain.dto.req.DomainUploadDTO;
import com.nageoffer.shortlink.domain.dto.resp.DomainRespDTO;
import com.nageoffer.shortlink.domain.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nageoffer.shortlink.domain.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

@Service
@RequiredArgsConstructor
public class DomainServiceImpl extends ServiceImpl<DomainInfoMapper, DomainInfoDO> implements DomainService {

    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public IPage<DomainRespDTO> listAllDomain(DomainReqDTO requestParam){
//        if(!checkUser(requestParam.getUsername(), requestParam.getToken())){
//            throw new ClientException("用户未登录！");
//        }
        LambdaQueryWrapper<DomainInfoDO> queryWrapper = Wrappers.lambdaQuery(DomainInfoDO.class)
                .eq(DomainInfoDO::getIsPurchased, 0);
        IPage<DomainInfoDO> queryResult = baseMapper.selectPage(requestParam, queryWrapper);
        if(queryResult.getRecords().isEmpty())
            throw new ClientException("当前没有域名出售，感谢您的支持！");
        return queryResult.convert(each-> BeanUtil.toBean(each, DomainRespDTO.class));
    }
    private boolean checkUser(String username, String token){
        return stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY+username, token) != null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void uploadDomain(DomainUploadDTO requestParam){
        int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, DomainInfoDO.class));
        if(inserted < 1)
            throw new ServiceException("域名上传失败");
    }
}
