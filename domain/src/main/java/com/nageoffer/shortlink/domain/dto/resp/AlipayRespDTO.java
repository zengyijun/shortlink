package com.nageoffer.shortlink.domain.dto.resp;

import lombok.Data;

@Data
public class AlipayRespDTO {
    private String form;
    private Long orderId;
    private boolean status;
}
