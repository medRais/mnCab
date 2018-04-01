package com.mn.mncab.DAO;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.mn.mncab.entities.ClientEntity;

@Transactional
public interface ClientDAO extends CrudRepository<ClientEntity, Long>{
	
	public ClientEntity findByFirstName(String name);
	
	public ClientEntity findById(long id);
}
