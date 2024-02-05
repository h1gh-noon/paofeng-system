package com.paofeng.system.domain;


import com.paofeng.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class SysRider extends BaseEntity {

    private Long riderId;
    private String riderName;
    private Long userId;
    private String sex;
    private String cardId;
    private String riderPhoto;
    private String cardPhotoZ;
    private String cardPhotoB;
    private BigDecimal riderMoney;
    private String status;
    private Date createTime;


    public Long getRiderId() {
        return riderId;
    }

    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }


    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }


    public String getRiderPhoto() {
        return riderPhoto;
    }

    public void setRiderPhoto(String riderPhoto) {
        this.riderPhoto = riderPhoto;
    }


    public String getCardPhotoZ() {
        return cardPhotoZ;
    }

    public void setCardPhotoZ(String cardPhotoZ) {
        this.cardPhotoZ = cardPhotoZ;
    }


    public String getCardPhotoB() {
        return cardPhotoB;
    }

    public void setCardPhotoB(String cardPhotoB) {
        this.cardPhotoB = cardPhotoB;
    }


    public BigDecimal getRiderMoney() {
        return riderMoney;
    }

    public void setRiderMoney(BigDecimal riderMoney) {
        this.riderMoney = riderMoney;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
