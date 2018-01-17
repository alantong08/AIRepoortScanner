package com.citi.alan.myproject.tess4j.dao;

import java.util.List;

import com.citi.alan.myproject.tess4j.entity.BlackMerchant;


public interface BlackMerchantDaoCustom {

	void batchInsert(List<BlackMerchant> blackMerchants);
	
}
