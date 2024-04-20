package com.sky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户账号
    private String account;

    // 用户密码
    private String password;

    //微信用户唯一标识
    private String openid;

    //姓名
    private String name;

    //手机号
    private String phone;

    //性别 0 女 1 男
    private String sex;

    //身份证号
    private String idNumber;

    //头像
    private String avatar;

    //注册时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

    // 用户签名
    private String signature;

    // 星海币
    private BigDecimal xinghaibi;

    /**
     * 上下线状态 1在线 2离线
     */
    @TableField("active_status")
    private Integer activeStatus;

    /**
     * 最后上下线时间
     */
    @TableField("last_opt_time")
    private Date lastOptTime;

    /**
     * 最后上下线时间
     */
    @TableField(value = "ip_info", typeHandler = JacksonTypeHandler.class)
    private IpInfo ipInfo;

    /**
     * 佩戴的徽章id
     */
    @TableField("item_id")
    private Long itemId;

    /**
     * 用户状态 0正常 1拉黑
     */
    @TableField("status")
    private Integer status;

    // 用户背景图
    private String backgroundUrl;

    // 用户等级
    private Integer lv;

    // 用户经验值
    private Integer experience;

    // 用户获赞
    private Long thumb;

    // 用户签到币
    private Integer signIcon;

    // 用户连续签到的天数
    private Integer signDay;

    // 用户是否进行其拿到
    private Integer isSign;

    // 人民币
    private Integer rmb;

    // 用户标签
    private String tags;
}
