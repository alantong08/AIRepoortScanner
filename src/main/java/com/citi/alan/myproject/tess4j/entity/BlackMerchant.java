package com.citi.alan.myproject.tess4j.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "black_merchant")
public class BlackMerchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String merchantNum;
    private String merchantName;
    

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public String getMerchantNum() {
        return merchantNum;
    }
    public void setMerchantNum(String merchantNum) {
        this.merchantNum = merchantNum;
    }
    
    
    
    
    
    
}
