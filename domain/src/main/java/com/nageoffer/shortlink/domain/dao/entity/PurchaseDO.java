package com.nageoffer.shortlink.domain.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_purchase")
public class PurchaseDO {
    private Long id;
    private Long uid;
    private Long did;
    private String domain;
    @TableField(value = "purchase_time")
    private Date purchaseTime;
    private Date expireTime;

//    @TableField("expire_flag")
    private int expireFlag;
}
