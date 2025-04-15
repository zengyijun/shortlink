package com.nageoffer.shortlink.domain.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration(value = "AlipayClientConfig")
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {

        private String appId;
        private String appPrivateKey;
        private String alipayPublicKey;
        private String serverUrl;
        private String notifyUrl;
        private String logPath;
        private String signType;
        private String charset;
        private String format;

        @Bean
        public AlipayClient alipayClient(){
                return new DefaultAlipayClient(serverUrl, appId,
                        appPrivateKey, format, charset, alipayPublicKey,
                        signType);
        }

}
