package com.nageoffer.shortlink.domain.dto.resp;

import lombok.Data;

@Data
public class DomainRespDTO {
    private String domain;
    private int yearPrice;
    private int monthPrice;
}
