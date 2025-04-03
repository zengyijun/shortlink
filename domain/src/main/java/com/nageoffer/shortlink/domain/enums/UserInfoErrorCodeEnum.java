package com.nageoffer.shortlink.domain.enums;

import com.nageoffer.shortlink.domain.common.convention.errorcode.IErrorCode;

public enum UserInfoErrorCodeEnum implements IErrorCode {
    USER_DOMAIN_EXIST("A000400", "当前域名已添加"),
    INFO_SAVE_ERROR("A000401", "域名新增失败");

    private final String code;
    private final String message;

    UserInfoErrorCodeEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
