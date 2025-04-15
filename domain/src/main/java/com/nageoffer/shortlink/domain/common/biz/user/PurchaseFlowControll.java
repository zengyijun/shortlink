package com.nageoffer.shortlink.domain.common.biz.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class PurchaseFlowControll {
    private final StringRedisTemplate redisTemplate;

    private static final String Flow_control_path = "lua/token_bucket.lua";

    public boolean isAllowed(String username, String domain, Long did){
        Long res = redisTemplate.execute(
                new DefaultRedisScript<>(Flow_control_path, Long.class),
                Collections.singletonList("rate_limit:" + domain)
        );
        return res != null && res == 1;
    }


}
