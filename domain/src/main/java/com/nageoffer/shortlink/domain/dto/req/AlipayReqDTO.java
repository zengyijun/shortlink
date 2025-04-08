package com.nageoffer.shortlink.domain.dto.req;

import lombok.Data;

@Data
public class AlipayReqDTO {
    public String orderId;
    public String cost;
    public String subject;
    public String domain;
}
