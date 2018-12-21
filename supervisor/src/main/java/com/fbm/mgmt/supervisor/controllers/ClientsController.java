package com.fbm.mgmt.supervisor.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fbm.mgmt.supervisor.dataobjects.CleaningType;
import com.fbm.mgmt.supervisor.dataobjects.Client;
import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.service.I_CleaningSpotService;
import com.fbm.mgmt.supervisor.service.I_CleaningTypeService;
import com.fbm.mgmt.supervisor.service.I_ClientService;

@RestController
@RequestMapping("/services/clients/")
public class ClientsController {

	private final Logger log = LoggerFactory.getLogger(ClientsController.class);

	@Autowired
	private I_ClientService clientService;

	@Autowired
	private I_CleaningSpotService cleaningSpotService;

	@Autowired
	private I_CleaningTypeService cleaningTypeService;

	@RequestMapping("/getAllClients")
	@ResponseBody
	public FbmResponse<List<Client>> getAllClients() {
		return clientService.getAllClients();
	}

	@PostMapping("/addClient")
	@ResponseBody
	public FbmResponse<Client> addClient(@RequestBody Client client) {
		FbmResponse<Client> res = clientService.addClient(client);
		return res;
	}

	@PostMapping("/deleteClients")
	@ResponseBody
	public FbmResponse<Boolean> deleteClient(@RequestBody List<Client> clients) {
		for(Client client : clients) {
			clientService.deleteClient(client);
		}
		FbmResponse<Boolean> res = new FbmResponse<Boolean>(true, null, true);
		return res;
	}

	@RequestMapping("/getAllCts")
	@ResponseBody
	public List<CleaningType> getAllCts() {
		return cleaningTypeService.getAllCleaningTypes();
	}

	@RequestMapping("/getAllCleaningTypes")
	@ResponseBody
	public List<CleaningType> getAllCleaningTypes() {
		List<CleaningType> cleaningTypes = cleaningTypeService.getAllCleaningTypes();
		if(cleaningTypes != null) {
			for(CleaningType ct : cleaningTypes) {
				ct.setCleaningSpots(cleaningSpotService.getAllCleaningSpots(ct.getId()));
			}
		}
		return cleaningTypes;
	}

	@RequestMapping("/getAllClientsFromClientType")
	@ResponseBody
	public FbmResponse<List<Client>> getAllClientsFromClientType(int clientTypeId) {
		return clientService.getAllClientsFromClientType(clientTypeId);
	}
}
