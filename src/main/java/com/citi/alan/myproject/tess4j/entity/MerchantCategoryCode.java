package com.citi.alan.myproject.tess4j.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "merchant_category_code")
public class MerchantCategoryCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String merchantCategory;
    private String merchantCode;
    private boolean isCmbBlackCode;
    

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMerchantCategory() {
        return merchantCategory;
    }
    public void setMerchantCategory(String merchantCategory) {
        this.merchantCategory = merchantCategory;
    }
    public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }
    public boolean isCmbBlackCode() {
        return isCmbBlackCode;
    }
    public void setCmbBlackCode(boolean isCmbBlackCode) {
        this.isCmbBlackCode = isCmbBlackCode;
    }

    

    
    
    
    
}
