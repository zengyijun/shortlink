package com.nageoffer.shortlink.domain.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_order")
public class AlipayDO {
    private Long orderId;
    private String subject;
    private int cost;
    private Date create_time;
    private int status;
}
