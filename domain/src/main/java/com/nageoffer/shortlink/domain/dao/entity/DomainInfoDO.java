package com.nageoffer.shortlink.domain.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_domain")
public class DomainInfoDO {
    @TableField(value = "d_id")
    private Long did;
    @TableField(value = "domain")
    private String domain;
    @TableField(value = "year_price")
    private int yearPrice;
    @TableField(value = "month_price")
    private int monthPrice;


    @TableField(value = "is_purchased", fill = FieldFill.INSERT)
    private int isPurchased = 0;
}