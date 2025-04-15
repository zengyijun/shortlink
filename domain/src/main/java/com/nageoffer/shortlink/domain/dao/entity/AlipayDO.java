package com.nageoffer.shortlink.domain.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_order")
public class AlipayDO {
    private Long orderId;
    private String username;
    private String subject;
    private int cost;
    private String domain;
    private Date createTime;
    private int status;
    private String alipayId;
}
