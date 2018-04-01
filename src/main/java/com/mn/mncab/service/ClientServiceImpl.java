package com.mn.mncab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mn.mncab.DAO.ClientDAO;
import com.mn.mncab.entities.ClientEntity;

@Service
public class ClientServiceImpl implements IClientService{
	
	@Autowired
	private ClientDAO clientDAO;

	@Override
	public ClientEntity findById(long id) {
		ClientEntity currentUser=clientDAO.findOne(id);
		return currentUser;
	}

	@Override
	public List<ClientEntity> findAllClients() {
		return (List<ClientEntity>)clientDAO.findAll();
	}

	@Override
	public void deleteClient(ClientEntity client) {
		clientDAO.delete(client);
	}

	public void saveClient(ClientEntity client) { 
		clientDAO.save(client);
		
	}

}
