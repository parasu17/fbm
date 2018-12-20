var checkLists;


// common utility methods
function isEmpty(str) {
	return (!str || 0 === str.length);
}

function isNumeric(n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
}

function getNumber(strNum, defVal) {
	if(isNumeric(strNum)) {
		return parseInt(strNum);
	}
	return defVal;
}

// logic functions for altering UI
function changeCheckList() {
	var combo = document.getElementById("clientTypeId");
	var selIndex = combo.selectedIndex;
	if(selIndex != -1) {
		populateCheckListTable(getCheckListTable(), checkLists.checkList[selIndex]);
	}
}

// AJAX calls
function inspectionPage() {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	checkLists = JSON.parse(this.responseText);
	    	createInspectionPage();
	    }
	  };
	  xhttp.open("GET", "conf/checklist_form.json", true);
	  xhttp.send();
}

function createInspectionPage() {
	clearPage();

	var parent = getRoot();
	
	parent.appendChild(createClientLabel());
	
	var combo = createClientTypeSelect();
	parent.appendChild(combo);
	populateOptions(combo);
	
	var table = createCheckListTable();
	parent.appendChild(table);
	populateCheckListTable(table, checkLists.checkList[0]);
	
	parent.appendChild(createCheckListSubmitButton());
}

function saveCheckList() {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	alert("Checklist saved successfully.");
	    }
	  };
	  xhttp.open("POST", "/services/inspection/saveCheckList", true);
	  //xhhtp.setRequestHeader("Accept", "application/json; charset=utf-8");
	  xhttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
	  var body = JSON.stringify(getCheckListData());
	  xhttp.send(body);
}

//populating UI while loading page
function populateOptions(combo) {
	combo.options = [];
	var listIndex;
	var checkListName;
	for(listIndex = 0;listIndex < checkLists.checkList.length ; listIndex++) {
		checkListName = checkLists.checkList[listIndex].checklistName;
		combo.options.add( new Option(checkListName, checkListName));
	}
}

function createClientLabel() {
	var divEle = document.createElement("div");
	divEle.innerHTML = '<label for="clientTypeId">Client Type</label>';
	return divEle.firstChild;
}

function createClientTypeSelect() {
	var divEle = document.createElement("div");
	divEle.innerHTML = '<select id="clientTypeId" onchange="changeCheckList()"></select>';
	return divEle.firstChild;
}

function createCheckListTable() {
	var divEle = document.createElement("div"); 
	divEle.innerHTML = '<table id="checkListTable"></table>';
	return divEle.firstChild;
}

function createCheckListSubmitButton() {
	var divEle = document.createElement("div"); 
	divEle.innerHTML = '<div class="button"><button type="button" onclick="saveCheckList()">Submit Checklist</button></div>';
	return divEle.firstChild;
}

function getCheckListTable() {
	return document.getElementById("checkListTable");
}

function populateCheckListTable(table, checkListJson) {
	table.innerHTML = "";
	populateHeader(table);
	for(var rowIndex=0;rowIndex < checkListJson.table.rows.length ; rowIndex++) {
		populateRow(table, checkListJson.table.rows[rowIndex]);
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

function populateRow(table, jsonRow) {
	// Create an empty <tr> element and add it to the 1st position of the table:
	var row = table.insertRow(-1);
	var rowCount = row.rowIndex;

	// Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
	var cell1 = row.insertCell(0);
	cell1.innerHTML = '<div class="noWrapClass" id="place_' + rowCount + '_0" >' + jsonRow.labelName + '</div>';
	var cell2 = row.insertCell(1);
	cell2.appendChild(createScores('radioScore_' + rowCount + '_1'));
	var cell3 = row.insertCell(2);
	cell3.appendChild(createComments('comments_' + rowCount + '_2'));
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
	var checkList = JSON.parse('{}');
	checkList['clientType'] = document.getElementById("clientTypeId").value;
	checkList['scores'] = [];
	checkList['totalScore'] = getNumber(document.getElementById("checkListTableFooter").cells[1].innerHTML, 0);
	
	var table = getCheckListTable();
	for(var i = 1; i < ( table.rows.length - 1); i++) {
		checkList.scores.push(getRowAsJson(table.rows[i]));
	}
	return checkList;
}

function getRowAsJson(tableRow) {
	var singleRow = JSON.parse('{}');
	var rowIndex = "_" + tableRow.rowIndex + "_";
	singleRow['place'] = tableRow.cells[0].innerText.trim();
	singleRow['score'] = getScore(tableRow);
	singleRow['comments'] = document.getElementById('comments' + rowIndex + '2').value;
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
	for(var i = 0; i < (rowLength - 1); i++) {
		totalScore = totalScore + getScore(table.rows[i]);
	}
	
	var row = table.rows[rowLength - 1];
	row.cells[1].innerHTML = '' + totalScore;
}


// for logging purposes
function log(str) {
	if (window.console && window.console.log) {
		console.log(str);
	}
}