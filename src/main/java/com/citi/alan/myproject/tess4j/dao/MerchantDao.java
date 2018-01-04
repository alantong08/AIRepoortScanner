package com.citi.alan.myproject.tess4j.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.alan.myproject.tess4j.entity.Merchant;

@Repository
public interface MerchantDao extends CrudRepository<Merchant, String>{

	public Merchant findByMerchantId(String merchantId);
	
}
