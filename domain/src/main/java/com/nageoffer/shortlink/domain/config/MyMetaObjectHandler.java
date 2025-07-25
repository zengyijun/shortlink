package com.nageoffer.shortlink.domain.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Date;

@Primary
@Component(value = "myMetaObjectHandlerByDomain")
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject){
        strictInsertFill(metaObject, "createTime", Date::new, Date.class);
        strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
        strictInsertFill(metaObject, "delFlag", ()->0, Integer.class);
        strictInsertFill(metaObject, "purchaseTime", Date::new, Date.class);
        strictInsertFill(metaObject, "createTime", Date::new, Date.class);
        strictInsertFill(metaObject, "status", ()->0, Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject){
        strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
    }
}
