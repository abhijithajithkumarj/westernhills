package com.westernhills.westernhills.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class CsvDto {

    private String username;
    private String orderId;
    private Date orderDate;
    private String status;
    private Double totalPrice;
    private String paymentMode;
    private String productName;
    private Double totalSales;
    private int totalOrders;
}