package com.citi.alan.myproject.tess4j.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.alan.myproject.tess4j.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, Long>{

	public Role findByRoleName(String roleName);
	
   

}
