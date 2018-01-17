package com.citi.alan.myproject.tess4j.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.alan.myproject.tess4j.entity.MerchantCategoryCode;

@Repository
public interface MerchantCategoryCodeDao extends CrudRepository<MerchantCategoryCode, Long>{

	public MerchantCategoryCode findByMerchantCode(String merchantCode);
	
}
