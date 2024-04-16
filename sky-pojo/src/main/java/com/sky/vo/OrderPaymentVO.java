package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentVO implements Serializable {

    // private String nonceStr; //随机字符串
    // private String paySign; //签名
    // private String timeStamp; //时间戳
    // private String signType; //签名算法
    // private String packageStr; //统一下单接口返回的 prepay_id 参数值

    //订单号
    private String orderNumber;

    //付款方式
    private Integer payMethod;

    // 付款金额
    private Double amount;

    // 付款结果 1 == 成功 0 == 失败
    private Integer result;

}
