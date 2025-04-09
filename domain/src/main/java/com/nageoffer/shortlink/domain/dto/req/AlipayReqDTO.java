package com.nageoffer.shortlink.domain.dto.req;

import lombok.Data;

@Data
public class AlipayReqDTO {
    public Long orderId;
    public int cost;
    public String subject;
    public String domain;
}
