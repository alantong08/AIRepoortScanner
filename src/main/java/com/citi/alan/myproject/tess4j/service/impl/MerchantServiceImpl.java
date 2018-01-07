package com.citi.alan.myproject.tess4j.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.alan.myproject.tess4j.dao.MerchantDao;
import com.citi.alan.myproject.tess4j.entity.Merchant;
import com.citi.alan.myproject.tess4j.service.api.MerchantService;

@Service
public class MerchantServiceImpl implements MerchantService {
	private static Logger logger = Logger.getLogger(MerchantServiceImpl.class);
	
	@Autowired
	private MerchantDao merchantDao;

	public Map<String, Merchant> getMerchantMap() {
		logger.info("*****start to getMerchantMap()");
		Iterable<Merchant> iterable = merchantDao.findAll();
		Iterator<Merchant> iterator = iterable.iterator();
		Map<String, Merchant> map = new HashMap<String, Merchant>();
		while (iterator.hasNext()) {
			Merchant merchant = iterator.next();
			map.put(merchant.getMerchantId(), merchant);
		}
		return map;
	}
}
