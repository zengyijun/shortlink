package com.nageoffer.shortlink.domain.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    INIT(0, "初始化"),
    PAID(1, "已支付"),
    REFUNDING(2, "退款中"),
    REFUNDED(3, "已退款"),
    CLOSED(4, "已关闭");

    private final int code;
    private final String message;

    OrderStatusEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
}
