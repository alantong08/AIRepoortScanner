package com.citi.alan.myproject.tess4j.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.citi.alan.myproject.tess4j.dao.BlackMerchantDaoCustom;
import com.citi.alan.myproject.tess4j.entity.BlackMerchant;

@Repository
@Transactional(readOnly = true)
public class BlackMerchantDaoImpl implements BlackMerchantDaoCustom {

    @PersistenceContext
    protected EntityManager em;
    
    @Transactional
    public void batchInsert(List<BlackMerchant> blackMerchants) {
        int size = blackMerchants.size();
        for (int i = 0; i < size; i++) {
            em.persist(blackMerchants.get(i));
            if (i % 1000 == 0) {                    
                em.flush();
                em.clear();
            }
        }

    }

}
