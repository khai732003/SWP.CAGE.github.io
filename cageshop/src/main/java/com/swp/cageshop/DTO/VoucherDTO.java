package com.swp.cageshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDTO{
    private Long id;
    private LocalDateTime createDate;
    private String code;
    private String description;
    private double voucherAmount;
    private String voucherType;
    private LocalDateTime expiration_date;

}