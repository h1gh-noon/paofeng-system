package com.paofeng.system.domain;


import com.paofeng.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class SysShop extends BaseEntity {

    private Long shopId;
    private String shopName;
    private String address;
    private String shopPhoto;
    private String cardPhotoZ;
    private String cardPhotoB;
    private String businessLicense;
    private String cateringLicense;
    private Long userId;
    private BigDecimal shopMoney;
    private String status;
    private String nickName;
    private String phonenumber;
    private Date createTime;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getShopPhoto() {
        return shopPhoto;
    }

    public void setShopPhoto(String shopPhoto) {
        this.shopPhoto = shopPhoto;
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


    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }


    public String getCateringLicense() {
        return cateringLicense;
    }

    public void setCateringLicense(String cateringLicense) {
        this.cateringLicense = cateringLicense;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public BigDecimal getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(BigDecimal shopMoney) {
        this.shopMoney = shopMoney;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
