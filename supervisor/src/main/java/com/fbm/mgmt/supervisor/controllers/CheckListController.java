package com.fbm.mgmt.supervisor.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fbm.mgmt.supervisor.dataobjects.CheckList;
import com.fbm.mgmt.supervisor.dataobjects.Status;
import com.google.gson.Gson;

@RestController
@RequestMapping("/services/inspection/")
public class CheckListController {

	private final Logger log = LoggerFactory.getLogger(CheckListController.class);

	@RequestMapping("/saveCheckList")
	public Status saveCheckLists(@RequestBody CheckList checkList) {
		log.info("Inside saveCheckList\n" + new Gson().toJson(checkList));
		return new Status(0, "Success");
	}

}
