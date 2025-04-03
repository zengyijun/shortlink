package com.nageoffer.shortlink.domain.dto.resp;

import lombok.Data;

import java.util.Date;

@Data
public class PurchaseRespDTO {
    private String username;
    private String domain;
    private Date purchase_time;
    private boolean status;
}
