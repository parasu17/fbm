package com.fbm.mgmt.supervisor.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fbm.mgmt.supervisor.dataobjects.CheckListEntity;
import com.fbm.mgmt.supervisor.dataobjects.Status;
import com.google.gson.Gson;

@RestController
@RequestMapping("/services")
public class CheckListController {

	private final Logger log = LoggerFactory.getLogger(CheckListController.class);

	@RequestMapping("/saveCheckList")
	public Status saveCheckLists(@RequestBody CheckListEntity checkListEntity) {
		log.info("Inside saveCheckList\n" + new Gson().toJson(checkListEntity));
		return new Status(0, "Success");
	}

}
