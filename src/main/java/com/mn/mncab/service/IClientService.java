package com.mn.mncab.service;

import java.util.List;


import com.mn.mncab.entities.ClientEntity;

public interface IClientService {
	
	ClientEntity findById(long id);
	
	List<ClientEntity> findAllClients();
	
	void deleteClient(ClientEntity client);
	
	void saveClient(ClientEntity client);

}
