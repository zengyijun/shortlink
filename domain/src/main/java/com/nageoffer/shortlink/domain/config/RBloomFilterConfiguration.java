package com.nageoffer.shortlink.domain.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "rBloomFilterConfigurationByDomain")
public class RBloomFilterConfiguration {
    /**
     * 在用户购买前查询数据库
     */
    @Bean
    public RBloomFilter<String> domainPurchaseBloomFilter(RedissonClient redissonClient){
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("domainPurchaseBloomFilter");
        bloomFilter.tryInit(1000000000L, 0.001);
        return bloomFilter;
    }

}
