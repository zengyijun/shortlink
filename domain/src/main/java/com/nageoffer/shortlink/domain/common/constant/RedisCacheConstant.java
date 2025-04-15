package com.nageoffer.shortlink.domain.common.constant;

public class RedisCacheConstant {
//    用户记录添加分布式锁
    public static final String LOCK_USER_INFO_ADD_KEY = "short-link:lock_user_info-add";
    public static final String USER_LOGIN_KEY = "short-link:login:";
    public static final String DOMAIN_PURCHASE_KEY = "short-link:purchase_domain";
//    public static final String VIP_PURCHASE_KEY = "short-link:purchase_vip";
    public static final String PURCHASE_RATE_LIMIT_KEY = "short-link:purchase_rate_limit:";
}
