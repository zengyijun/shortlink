package com.nageoffer.shortlink.domain.dto.req;

import lombok.Data;

@Data
public class DomainUploadDTO {
    private String domain;
    private Integer year_price;
    private Integer month_price;
}
