package com.citi.alan.myproject.tess4j.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.alan.myproject.tess4j.entity.BlackMerchant;

@Repository
public interface BlackMerchantDao extends CrudRepository<BlackMerchant, Long>, BlackMerchantDaoCustom{

	public BlackMerchant findByMerchantNum(String merchantNum);
	
}
