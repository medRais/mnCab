package com.mn.mncab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mn.mncab.DAO.ClientDAO;
import com.mn.mncab.entities.ClientEntity;

@Controller
public class ClientController {

	@Autowired
	private ClientDAO clientDAO;

	@RequestMapping("/create")
	@ResponseBody
	public String create(String firstName, String lastName) {
		String clientId = "";
		try {
			ClientEntity client = new ClientEntity(firstName, lastName);
			clientDAO.save(client);
			clientId = String.valueOf(client.getId());
		} catch (Exception ex) {
			return "Error creating the client: " + ex.toString();
		}
		return "client succesfully created with id = " + clientId;
	}

	@RequestMapping("/get-by-name")
	@ResponseBody
	public String getByEmail(String name) {
		String clientID = "";
		try {
			ClientEntity client = clientDAO.findByFirstName(name);
			clientID = String.valueOf(client.getId());
		} catch (Exception ex) {
			return "User not found";
		}
		return "The client id is: " + clientID;
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updateClient(long id, String firstName, String lastName) {
		try {
			ClientEntity client = clientDAO.findOne(id);
			client.setFirstName(firstName);
			client.setLastName(lastName);
			clientDAO.save(client);
		} catch (Exception ex) {
			return "Error updating the client: " + ex.toString();
		}
		return "client succesfully updated!";
	}
	
	  @RequestMapping("/delete")
	  @ResponseBody
	  public String delete(long id) {
	    try {
	    	ClientEntity client = new ClientEntity(id);
	      clientDAO.delete(client);
	    }
	    catch (Exception ex) {
	      return "Error deleting the client:" + ex.toString();
	    }
	    return "client succesfully deleted!";
	  }

}
