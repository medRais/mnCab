package com.mn.mncab.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.mn.mncab.DAO.ClientDAO;
import com.mn.mncab.entities.ClientEntity;
import com.mn.mncab.service.IClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

	public static final Logger logger = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private IClientService clientService;

	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/client/add", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody ClientEntity client, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", client);

		clientService.saveClient(client);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/clients/client/{id}").buildAndExpand(client.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User
	// ------------------------------------------------

	@RequestMapping(value = "/client/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody ClientEntity client) {
		logger.info("Updating User with id {}", id);

		ClientEntity currentUser = clientService.findById(id);
		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity("Unable to upate. User with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}

		currentUser.setFirstName(client.getFirstName());
		currentUser.setLastName(client.getLastName());

		clientService.saveClient(currentUser);
		return new ResponseEntity<ClientEntity>(currentUser, HttpStatus.OK);
	}

	@RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteClient(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		ClientEntity client = clientService.findById(id);
		if (client == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity("Unable to delete. User with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		clientService.deleteClient(client);
		return new ResponseEntity<ClientEntity>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/allclients", method = RequestMethod.GET)
	public ResponseEntity<List<ClientEntity>> listAllClients() {
		// List<ClientEntity> users = userService.findAllUsers();
		List<ClientEntity> clients = new ArrayList<ClientEntity>();
		clients=clientService.findAllClients();
		logger.info("result size is: "+clients.size());
		if (clients.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<ClientEntity>>(clients, HttpStatus.OK);
	}

	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getClient(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		ClientEntity client = clientService.findById(id);
		if (client == null) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity("User with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ClientEntity>(client, HttpStatus.OK);
	}

//	@RequestMapping(value = "/clientByName/{name}", method = RequestMethod.GET)
//	public String getByName(@PathVariable String name) {
//		String clientID = "";
//		try {
//			ClientEntity client = clientDAO.findByFirstName(name);
//			clientID = String.valueOf(client.getId());
//		} catch (Exception ex) {
//			return "User not found";
//		}
//		return "The client id is: " + clientID;
//	}

}
