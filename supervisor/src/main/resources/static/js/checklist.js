

// logic functions for altering UI
function changeCheckList(select) {
	var client = getClient(select.value);
	if(client == null) {
		getCheckListTable().innerHTML = '';
	} else {
		populateCheckListTable(getCheckListTable(), client);
	}
	document.getElementById("feedbackId").value = '';
}

// AJAX calls
function inspectionPage() {
	if(loggedInUser == null || (typeof loggedInUser.id === 'undefined')) {
		return;
	}
	//getAllClientsWithCleaningTypes(null, null, createInspectionPage);
	getGeoLocation(showGeoBasedClients, showAllClients);
}

function showGeoBasedClients(position) {
	 getAllClientsWithCleaningTypes(position.coords.latitude, position.coords.longitude, createInspectionPage);
}

function showAllClients(errorMsg) {
	alert(errorMsg + " Hence showing all the clients");
	getAllClientsWithCleaningTypes(null, null, createInspectionPage);
}

function clientTypeExists(clientTypes, clientType) {
	return clientTypes.some(function(el) {
		return el.name === clientType;
	});
}

function getClient(clientId) {
	for(var i in clients) {
		if(clients[i].id == getNumber(clientId, -1)) {
			return clients[i];
		}
	}
	return null;
}

function getClientTypes(clientList) {
	var clientTypes = [];
	if(!clientList) {
		return clientTypes;
	}
	var cleaningType;
	for(var i in clientList) {
		if(clientList[i].cleaningType != null && !clientTypeExists(clientTypes, clientList[i].cleaningType.name)) {
			clientTypes.push(clientList[i].cleaningType);
		}
	}
	return clientTypes;
}

function createInspectionPage(clientList) {
	clearPage();
	clients = clientList;

	var parent = getRoot();
	
	parent.appendChild(createInspectionInputsTable(clientList));
	parent.appendChild(createCheckListTable());
	parent.appendChild(createFeedback());
	parent.appendChild(createCheckListSubmitButton());
}

function validateCheckListBeforeSubmit() {
	var client_id = getNumber(document.getElementById("clientId").value, -1);
	if(client_id == -1) {
		alert("Please select the client to submit the Inspection Report");
		return false;
	}
	
	var total_score = getNumber(document.getElementById("checkListTableFooter").cells[1].innerHTML, 0);
	if(total_score == 0) {
		alert("None of the tasks are inspected. Please inspect the tasks");
		return false;
	}
	
	return true;
}

function saveCheckList() {
	if (!validateCheckListBeforeSubmit()) {
		return;
	}
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			alert("Checklist saved successfully.");
		}
	};
	xhttp.open("POST", "services/inspection/saveInspectionReport", true);
	xhttp.setRequestHeader("Accept", "application/json; charset=utf-8");
	xhttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
	var body = JSON.stringify(getCheckListData());
	xhttp.send(body);
}

function createInspectionInputsTable(clientList) {
	var table = '<table class="inspectionInputsTable" id="inspectionInputsTable">';
	
	table += '<tr><td><label for="clientTypeId">Client Type</label></td>';
	table += '<td>' + createClientTypeSelect(clientList) + '</td></tr>';
	
	table += '<tr><td><label for="clientId">Client</label></td>';
	table += '<td>' + createClientSelect(clientList) + '</td></tr>';
	table += '</table>';
	
	return createDomElement(table);
}


//populating UI while loading page
function createClientTypeSelect(clientList) {
	var clientTypeSelect = '<select id="clientTypeId" onchange="changeClientList(this)">';
	var options_str = '<option value=""></option>';
	var clientTypes = getClientTypes(clientList);
	clientTypes.forEach( function(clientType) {
	  options_str += '<option value="' + clientType.id + '">' + clientType.name + '</option>';
	});

	clientTypeSelect = clientTypeSelect + options_str + '</select>';
	return clientTypeSelect;
}

function createClientSelect(clientList) {
	var clientSelect = '<select id="clientId" onchange="changeCheckList(this)">';
	var options_str = '<option value=""></option>';

	clientSelect = clientSelect + options_str + '</select>';
	return clientSelect;
}

function changeClientList(select) {
	var clientSelect = document.getElementById('clientId');
	var options_str = '<option value=""></option>';
	clients.forEach(function(client) {
		if(client.cleaning_type_id === getNumber(select.value, -1)) {
			options_str += '<option value="' + client.id + '">' + client.client_name + '</option>';
		}
	});
	clientSelect.innerHTML = options_str;
	getCheckListTable().innerHTML = '';
	document.getElementById("feedbackId").value = '';
}

function createCheckListTable() {
	return createDomElement('<table id="checkListTable"></table>');
}

function createFeedback() {
	var feedbackStr = '<div class="feedbackClass">Feedback :<br><textarea rows="4" cols="50" maxlength="950" name="comment" id="feedbackId" placeholder="Enter your feedback here"></textarea></div>';
	return createDomElement(feedbackStr);
}

function createCheckListSubmitButton() {
	return createDomElement('<div class="button"><button type="button" onclick="saveCheckList()">Submit Checklist</button></div>');
}

function getCheckListTable() {
	return document.getElementById("checkListTable");
}

function populateCheckListTable(table, client) {
	table.innerHTML = "";
	populateHeader(table);
	if (client.cleaningType != null && client.cleaningType.cleaningSpots != null) {
		for (var rowIndex = 0; rowIndex < client.cleaningType.cleaningSpots.length; rowIndex++) {
			populateRow(table, client.cleaningType.cleaningSpots[rowIndex]);
		}
	}
	populateFooter(table);
}

function populateHeader(table) {
	var header = table.createTHead();
	var row = header.insertRow(0);
	row.className = 'checkListTableHeader';
	var cell1 = row.insertCell(0);
	cell1.innerHTML = "Place";
	cell1.className = "contentWidthClass noWrapClass";
	var cell2 = row.insertCell(1);
	cell2.innerHTML = "Score";
	cell2.className = "contentWidthClass noWrapClass";
	var cell3 = row.insertCell(2);
	cell3.innerHTML = "Comments";
}

function populateFooter(table) {
	var row = table.insertRow(-1);
	row.id = 'checkListTableFooter';
	row.className = 'checkListTableFooter';
	var cell1 = row.insertCell(0);
	cell1.innerHTML = "Total Score";
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
}

function populateRow(table, cleaningSpot) {
	// Create an empty <tr> element and add it to the 1st position of the table:
	var row = table.insertRow(-1);
	var rowCount = row.rowIndex;

	// Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
	var cell1 = row.insertCell(0);
	var cellDiv = '<div class="noWrapClass';
	if(cleaningSpot.heading) {
		cellDiv += ' tableHeaderClass';
	}
	cellDiv += '" id="place_' + rowCount + '_0" >' + cleaningSpot.spt_name + '</div>';
	cell1.innerHTML = cellDiv;
	var cell2 = row.insertCell(1);
	if(! cleaningSpot.heading) {
		cell2.appendChild(createScores('radioScore_' + rowCount + '_1'));
	}
	var cell3 = row.insertCell(2);
	if(! cleaningSpot.heading) {
		cell3.appendChild(createComments('comments_' + rowCount + '_2'));
	}
}

function createScores(name) {
	var score = '';
	score = score + '<input id="' + name + '_0" type="radio" name="' + name + '" value="0" onclick="calculateScore()" >0</input> &nbsp; &nbsp; &nbsp; &nbsp;';
	score = score + '<input id="' + name + '_1" type="radio" name="' + name + '" value="1" onclick="calculateScore()" >1</input> &nbsp; &nbsp; &nbsp; &nbsp;';
	score = score + '<input id="' + name + '_2" type="radio" name="' + name + '" value="2" onclick="calculateScore()" >2</input> &nbsp; &nbsp; &nbsp; &nbsp;';
	score = score + '<input id="' + name + '_3" type="radio" name="' + name + '" value="3" onclick="calculateScore()" >3</input> &nbsp; &nbsp; &nbsp; &nbsp;';
	
	var radioFragment = document.createElement('div');
	radioFragment.id = name;
	radioFragment.className = 'noWrapClass';
    radioFragment.innerHTML = score;
    
    return radioFragment;
}

function createComments(name) {
	var comment = '';
	comment = comment + '<input class="checkListTableCommentsCellClass" id="' + name + '" type="text" name="' + name + '" />';
	
	var commentFragment = document.createElement('div');
	commentFragment.innerHTML = comment;
    
    return commentFragment;
	
}


// getting data from checkList table as a JSON object
function getCheckListData() {
	/*
	private int id;
	private int client_id;
	private int supervisor_id;
	private long date;
	private String feedback;
	private int totalScore;
	private int score_percent;
	private Score[] scores;
	 */
	var inspectionReport = {};
	inspectionReport.client_id = getNumber(document.getElementById("clientId").value, -1);
	inspectionReport.supervisor_id = loggedInUser.id;
	var start_of_today = new Date();
	start_of_today.setHours(0,0,0,0);
	inspectionReport.date = start_of_today.getTime();
	inspectionReport.feedback = document.getElementById("feedbackId").value;
	inspectionReport.totalscore = getNumber(document.getElementById("checkListTableFooter").cells[1].innerHTML, 0);
	inspectionReport.score_percent = getNumber(document.getElementById("checkListTableFooter").cells[2].innerHTML, 0);
	inspectionReport.scores = [];
	var table = getCheckListTable();
	for(var i = 1; i < ( table.rows.length - 1); i++) {
		inspectionReport.scores.push(getRowAsJson(table.rows[i]));
	}
	return inspectionReport;
}

function getRowAsJson(tableRow) {
	/*
	private int id;
	private int inspection_report_id;
	private int cleaning_spot_name;
	private int score;
	private String comments;
	 */
	var singleRow = {};
	var rowIndex = "_" + tableRow.rowIndex + "_";
	singleRow.cleaning_spot_name = tableRow.cells[0].innerText.trim();
	singleRow.score = getScore(tableRow);
	singleRow.comments = tableRow.cells[2].innerText.trim();
	return singleRow;
}

function getScore(tableRow) {
	var rowIndex = "_" + tableRow.rowIndex + "_1_";
	var scoreValue = 0;
	for(var i = 0; i < 4; i++) {
		var radio = document.getElementById("radioScore" + rowIndex + i);
		if(radio && radio.checked) {
			scoreValue = parseInt(radio.value);
			return scoreValue;
		}
	}
	return scoreValue;
}

function calculateScore() {
	var table = getCheckListTable();
	var totalScore = 0;
	var rowLength = table.rows.length;
	for(var i = 1; i < (rowLength - 1); i++) {
		totalScore = totalScore + getScore(table.rows[i]);
	}
	
	var row = table.rows[rowLength - 1];
	row.cells[1].innerHTML = '' + totalScore;
	var score_percent = ( totalScore / ( getValidRowCount(table) * 3) ) * 100;
	row.cells[2].innerHTML = '' + score_percent;
}

function getValidRowCount(table) {
	var row;
	var rowCount = 0;
	for(var i = 0; i < table.rows.length; i++) {
		row = document.getElementById('radioScore_' + i + '_1');
		if(row) {
			rowCount += 1;
		}
	}
	return rowCount;
}

// for logging purposes
function log(str) {
	if (window.console && window.console.log) {
		console.log(str);
	}
}