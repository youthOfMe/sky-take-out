package com.chenhai.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrdersPaymentDTO implements Serializable {
    //订单号
    private String orderNumber;

    //付款方式
    private Integer payMethod;

    // 付款金额
    private BigDecimal amount;

}
