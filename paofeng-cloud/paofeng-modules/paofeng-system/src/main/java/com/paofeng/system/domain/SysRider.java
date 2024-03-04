package com.paofeng.system.domain;


import com.paofeng.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
public class SysRider extends BaseEntity {

    private Long riderId;
    private String riderName;
    private Long userId;
    private String sex;
    private String cardId;
    private String secondPhone;
    private String phonenumber;
    private String riderPhoto;
    private String cardPhotoZ;
    private String cardPhotoB;
    private BigDecimal riderMoney;
    private String status;
    private Date createTime;

}
