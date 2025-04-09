package com.nageoffer.shortlink.domain.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration(value = "AlipayClientConfig")
public class AlipayConfig {

        // 商户appid
        public static String APPID = "2021000147675709";

        // 私钥 pkcs8格式的
        public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQChqcCPH+B30toPNhILl87liZqalMf8OPx8EWJcl4hS6PRVR1+oF4zrs8uRrShKm367bdNbBZXXyKgHz1NB5eIW1cRXTxO2AkzBeCw2WWB7Slbqtm1FVQVWvNkAYocHtQRDqZ3HIljALmEI54pmGe4anysrr8xfDOVMEXBFQ21fmDfWPmrsdP+3JHyc3oRiFjRPCgJddIYROwPtKgHvNQUP4kiS9bPXN9QE55uxj3yZ3p8XHu8yRz0PFqNKkbQLi1fsfu5l2i0mw5XucRNX5ZVbW7/vMhv7+mykPAnwuF/Aqo/LnB89cHZztQ0yfaoCvwMZ74R6PXp1QyR+BvyEoqwvAgMBAAECggEAQoQK05jFDeg2im2v7Q8wNKdycknnIvL7h7zLYNeEYsCwz2nHhOzzsYNPrge1/USPV2TvwpLAvYE1CcmXMxT3+ndICrhhntDBbhUY89/AX9oNexNGUHGT+cVH7+2aK2SidYiTf3y1UZWDnVtPSYU/v7pf0+DIUQ+IRM0kCYuds+fQBbrPDz6tTzWd78n4n1ALHji6TZj3p3CXRLDLpD/N3O7pjRgd1RbRi8XkdMZ7bZ8ernbQhQcBx1goKHhDavizxleflVJElo2SCnUemIFA3aNFb3pSYoObOuYu9AYXte/BkHBGlwAKncA6vIB5J2Fq43QFGxt8P0v3D2LZSpIhkQKBgQDkN3izBH/m8BHjr0Nslkw4nzibgDsco16Kby3tJ3mFG2UraHTtf0fD4hf5WdVTlgXo2xy1aNSY0lIL0HMJCZog7OYS/NqPbjAcjHuFl98jJ8FKFwQZOLkUsgaVmbln3p8KCeeaDMeggV2q1H6SavdGiKMuh6keOGYKanpPo7EguQKBgQC1WBe6TEpOyfMwxwYhqrmtoFg1aG3aZr+Alz8/H0BdRGjZfoa1hy6hbF1iO/o47PYXnbuolyUM+DHl/oQjgCHuDDhVnV4ePXf3CsuSWUGgea9plLM/d7G7wk8IOPxU39aIbC1Y8keCUV27IIz4eUCUmZZOV4MKTvTyZgNvqKQwJwKBgQC4oRgI3C8NilMuvG/zvXRUqpuFUiyAcrrdjCy/nBv3z739bDiMm1hX5OY4WLD5Onn1dK65JOI58I1n+W/Xc+k5ke5glifLuf06W3zjC4b+Tf9WDWDHdthlIdGp3f/dRIGuAW1Zd9VYyNAakGcF1XvPmGNMFXyAcmg7LQFexLNcIQKBgQCQa6zR6GoRX1H3GbmvXEjtltJJDnbWRZfjsBBlk9ztqkqaU+KydQtU8XlZf4yig0zlt++4r06Jiqwqj8zqfOQIseC3hlFrYebFbup7DexvAhoJyqwKMqfwcSoB0Whbq7wt/4EJ4kHC7kvoeF7sj5w3/C0xDQ7GSeVXmpjH9xVzZwKBgHwE3T3Vd3fB8spo/hCc3QZ8BFlfSR52Wn00veJsBojZds/brDQpvzma+MqUiFqesVxudAyQPpsZaMTNtvcJesEpLdHRgEjVv+UVcsvKa4PBJvapTOIdhF+UflnnPU04yeMHpwGHDO+9C9nBNTVRLr9W8kr/mMUjcRSyIx0vQI78";
        // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
        public static String notify_url = "http://2sjkd3.natappfree.cc";

        // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
        public static String return_url = "";

        // 请求网关地址  正式的网关:https://openapi.alipay.com/gateway.do  沙箱的: https://openapi.alipaydev.com/gateway.do
        public static String serverUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

        // 编码
        public static String CHARSET = "UTF-8";

        // 返回格式
        public static String FORMAT = "json";

        // 支付宝公钥
        public static String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjNgh6/KTmj+L6m62KLF+Pqx+JYZkgpwdvJhyv/UW6sVZ5P3i2tljQcjdz82DeJfPkg9BBK76yBCt5eR5Pks3TcgFzzOwk/x8IhU/WY9AJGQK8kZjzQHuL8CBHDG6X8tPE2DhbMJvK3KlpAXVacJPe/ZT+hmC+iqzd8LaQYQIJdhZbIzHIB1pgx3HqFHJ7iYJMIvQnZ6q0MNVR8tY1JGdKwWHbQALszByk7DdCYbEq2QTVWFcWK5jZ0NuamOy4Nnpn4I3hykHnJ1sGOJpYco4so0sPsa/Y9+i455Y9xEDr72uec0lmSz2mJzWtxy/jWhTdOuq1z8out68Fao/eDdAjQIDAQAB";

        // 日志记录目录定义在 logFile 中
        public static String log_path = "/log";

        // RSA2
        public static String SIGNTYPE = "RSA2";

        @Bean
        public AlipayClient alipayClient(){
                return new DefaultAlipayClient(AlipayConfig.serverUrl, AlipayConfig.APPID,
                        AlipayConfig.RSA_PRIVATE_KEY,AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,
                        AlipayConfig.SIGNTYPE);
        }

}
