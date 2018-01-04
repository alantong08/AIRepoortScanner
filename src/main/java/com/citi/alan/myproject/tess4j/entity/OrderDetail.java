package com.citi.alan.myproject.tess4j.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String scanDate;
    private String orderNum;
    private String merchantName;
    private String actualPrice;
    private String discountedPrice;
    private String transferType;
    private String activityType;
    private String rate;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;
    private String createdDate;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    public String getScanDate() {
		return scanDate;
	}
	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}
	public String getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(String actualPrice) {
		this.actualPrice = actualPrice;
	}
	public String getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(String discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public String getTransferType() {
        return transferType;
    }
    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
    public String getActivityType() {
        return activityType;
    }
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    

	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public UserInfo getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
      
    }

}
