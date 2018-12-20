package com.fbm.mgmt.supervisor.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fbm.mgmt.supervisor.dataobjects.Client;
import com.fbm.mgmt.supervisor.service.I_ClientService;

@RestController
@RequestMapping("/services/clients/")
public class ClientsController {

	private final Logger log = LoggerFactory.getLogger(ClientsController.class);

	@Autowired
	private I_ClientService clientService;

	@RequestMapping("/getAllClients")
	@ResponseBody
	public List<Client> getAllClients() {
		return clientService.getAllClients();
	}

	@PostMapping("/addClient")
	@ResponseBody
	public Client addClient(@RequestBody Client client) {
		boolean result = clientService.addClient(client);
		return result ? client : null;
	}

	@PostMapping("/deleteClients")
	public ResponseEntity<HttpStatus> deleteClient(@RequestBody List<Client> clients) {
		for(Client client : clients) {
			clientService.deleteClient(client);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
