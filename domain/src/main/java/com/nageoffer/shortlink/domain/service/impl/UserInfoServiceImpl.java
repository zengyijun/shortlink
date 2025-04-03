package com.nageoffer.shortlink.domain.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.domain.common.convention.exception.ClientException;
import com.nageoffer.shortlink.domain.dao.entity.UserInfoDO;
import com.nageoffer.shortlink.domain.dao.mapper.UserInfoMapper;
import com.nageoffer.shortlink.domain.dto.req.UserInfoCreateDTO;
import com.nageoffer.shortlink.domain.dto.req.UserInfoReqDTO;
import com.nageoffer.shortlink.domain.dto.req.UserInfoUpdateDTO;
import com.nageoffer.shortlink.domain.dto.resp.UserInfoRespDTO;
import com.nageoffer.shortlink.domain.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nageoffer.shortlink.domain.common.constant.RedisCacheConstant.LOCK_USER_INFO_ADD_KEY;
import static com.nageoffer.shortlink.domain.common.constant.RedisCacheConstant.USER_LOGIN_KEY;
import static com.nageoffer.shortlink.domain.enums.UserInfoErrorCodeEnum.INFO_SAVE_ERROR;
import static com.nageoffer.shortlink.domain.enums.UserInfoErrorCodeEnum.USER_DOMAIN_EXIST;

/*
* 短链接接口实现层
* */

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoDO> implements UserInfoService {

    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createUserInfo(UserInfoCreateDTO requestParam){
        if(!checkLogin(requestParam.getUsername(), requestParam.getToken()))
            throw new ClientException("用户未登录");
        RLock lock = redissonClient.getLock(LOCK_USER_INFO_ADD_KEY + requestParam.getUsername() + requestParam.getDomain());
        if(!lock.tryLock()){
            throw new ClientException(USER_DOMAIN_EXIST);
        }
        try{
            // 查询是否存在
            LambdaQueryWrapper<UserInfoDO> queryWrapper = Wrappers.lambdaQuery(UserInfoDO.class)
                    .eq(UserInfoDO::getUsername, requestParam.getUsername())
                    .eq(UserInfoDO::getDomain, requestParam.getDomain())
                    .eq(UserInfoDO::getDelFlag, 0);
            UserInfoDO userInfoDO = baseMapper.selectOne(queryWrapper);
            if(userInfoDO != null){
                throw new ClientException(USER_DOMAIN_EXIST);
            }
            int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserInfoDO.class));
            if(inserted < 1) {
                throw new ClientException(INFO_SAVE_ERROR);
            }
        } catch (DuplicateKeyException ex) {
            throw new ClientException(USER_DOMAIN_EXIST);
        }
        finally {
            lock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UserInfoUpdateDTO requestParam) {
//    查询新连接是否合法
        if(!checkLogin(requestParam.getUsername(), requestParam.getToken()))
            throw new ClientException("用户未登录");
        LambdaQueryWrapper<UserInfoDO> queryWrapper = Wrappers.lambdaQuery(UserInfoDO.class)
                .eq(UserInfoDO::getUsername, requestParam.getUsername())
                .eq(UserInfoDO::getDomain, requestParam.getNew_domain())
                .eq(UserInfoDO::getDelFlag, 0);
        UserInfoDO userInfoDO = baseMapper.selectOne(queryWrapper);
        if (userInfoDO != null) {
            throw new ClientException("新的域名不合法！");
        }
        RLock lock = redissonClient.getLock(LOCK_USER_INFO_ADD_KEY + requestParam.getUsername() + requestParam.getOld_domain());
        if(!lock.tryLock()){
            throw new ClientException(USER_DOMAIN_EXIST);
        }
        try {
            LambdaUpdateWrapper<UserInfoDO> updateWrapper = Wrappers.lambdaUpdate(UserInfoDO.class)
                    .eq(UserInfoDO::getUsername, requestParam.getUsername())
                    .eq(UserInfoDO::getDomain, requestParam.getOld_domain())
                    .eq(UserInfoDO::getDelFlag, 0);
            UserInfoDO userInfodO = new UserInfoDO();
            userInfodO.setDomain(requestParam.getNew_domain());
            int is_updated = baseMapper.update(userInfodO, updateWrapper);
            if (is_updated == 0) {
                throw new ClientException("更新失败，信息不存在！");
            }
        } catch (DuplicateKeyException ex) {
            throw new ClientException(USER_DOMAIN_EXIST);
        } finally {
            lock.unlock();
        }
    }

    public IPage<UserInfoRespDTO> getDomain(UserInfoReqDTO requestParam){
        if(!checkLogin(requestParam.getUsername(), requestParam.getToken()))
            throw new ClientException("用户未登录");
        LambdaQueryWrapper<UserInfoDO> queryWrapper = Wrappers.lambdaQuery(UserInfoDO.class)
                .eq(UserInfoDO::getUsername, requestParam.getUsername())
                .eq(UserInfoDO::getDelFlag, 0);

        IPage<UserInfoDO> resultPage = baseMapper.selectPage(requestParam, queryWrapper);
        if(resultPage.getRecords().isEmpty())
            throw new ClientException("当前用户名下无域名");
        return resultPage.convert(each-> BeanUtil.toBean(each, UserInfoRespDTO.class));

    }


    @Override
    public boolean checkLogin(String username, String token){
        return stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY+username, token) != null;
    }

}
