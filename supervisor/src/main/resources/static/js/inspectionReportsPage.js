var inspectionReportsTabulator;

function inspectionReportsPage() {
	clearPage();
	createInspectionReportsPage();
}

function createInspectionReportsPage() {
	var parent = getRoot();
	inspectionReportsTabulator = createInspectionReportsTable();
	parent.appendChild(createInspectionReportDisplayPanel());
	refreshInspectionReports();
}

function createInspectionReportsTable() {
	var parent = getRoot();
	var inspectionReportsTableDiv = createInspectionReportsTableDomElement();
	parent.appendChild(inspectionReportsTableDiv);

	var cols = getInspectionReportsTableColumns();
	//create Tabulator on DOM element with id "clients-table"
	 var table = new Tabulator("#inspection-reports-table", {
	  	height:"50%", // set height of table (in CSS or here), this enables the Virtual DOM and improves render speed dramatically (can be any valid css height value)
	  	layout:"fitColumns", //fit columns to width of table (optional)
	  	columns:cols,
	  	selectable:true,
	    pagination:"local",
	    paginationSize:20,
	  	selectableRangeMode:"click",
	  	rowClick:displayInspectionReport
	 });
	 
	 return table;
}

function createInspectionReportsTableDomElement() {
	return createDomElement('<div id="inspection-reports-table"></div>');
}

function createInspectionReportDisplayPanel() {
	return createDomElement('<div class="inspection-report-display-panel" id="inspection-report-display-panel"></div>');
}

function getInspectionReportDisplayPanel() {
	return document.getElementById('inspection-report-display-panel');
}

function clearInspectionReportDisplayPanel() {
	if(getInspectionReportDisplayPanel()) {
		getInspectionReportDisplayPanel().innerHTML = '';
	}
}

function getInspectionReportsTableColumns() {
	/*
	create table clients (
			id SERIAL,
			client_name varchar(255),
			address varchar(255),
			city varchar(255),
			province varchar(255),
			pin varchar(20),
			country varchar(100),
			PRIMARY KEY (id)
		);
	*/

  	var columns = [ //Define Table Columns
 	 				{  title:"Client Name"     	, field:"client_name" 		, width:150     },
 	 				{  title:"Supervisor Name"  , field:"supervisor_name"   , align:"left"  },
 	 				{  title:"Date"     		, field:"date"        		, align: "left", formatter:formatDate },
 	 				{  title:"Total Score"		, field:"totalscore"    	, align:"left"  },
 	 				{  title:"Score Percentage" , field:"score_percent"     , align:"left"  },
 	 				{  title:"Feedback"  		, field:"feedback"     		, align:"left"  }
 	 			];
	
  	return columns;
}

function refreshInspectionReports() {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var fbmResponse = JSON.parse(this.responseText);
	    	if(fbmResponse.success) {
	    		inspectionReportsTabulator.setData(fbmResponse.responseData);
	    	} else {
	    		showAlert('Could not fetch all Inspection Reports from server');
	    	}
	    }
	  };
	  xhttp.open("GET", "services/inspection/getAllInspectionReports", true);
	  xhttp.send();
}

function displayInspectionReport(e, row) {
	clearInspectionReportDisplayPanel();

	var panel = getInspectionReportDisplayPanel();
	var ir = row.getData();
	createIrFields(panel, ir);
}

function createIrFields(panel, ir) {
	var fields = '<div id="inspection-report-display-summary"><table><tr><td>Client Name</td><td>' + ir.client_name + '</td></tr>';
	fields += '<tr><td>Supervisor Name</td><td>' + ir.supervisor_name + '</td></tr>';
	fields += '<tr><td>Date</td><td>' + formatDate(ir.date) + '</td></tr>';
	fields += '<tr><td>Total Score</td><td>' + ir.totalscore + '</td></tr>';
	fields += '<tr><td>Score Percent</td><td>' + ir.score_percent + '</td></tr>';
	fields += '<tr><td>Feedback</td><td>' + ir.feedback + '</td></tr>';
	fields += '</table></div>';
	
	panel.appendChild(createDomElement(fields));
	
	fields = '<div id="inspection-report-display-scores"><table><tr><th>Place</th><th>Score</th><th>Comments</th></tr>';
	for(var i in ir.scores) {
		fields += '<tr><td>' + ir.scores[i].cleaning_spot_name + '</td><td>' + ir.scores[i].score + '</td><td>' + ir.scores[i].comments + '</td></tr>';
	}
	fields += '</table></div>';
	panel.appendChild(createDomElement(fields));
}

function formatDate(cell, formatterParams, onRendered) {
	return formatDate(cell.getValue());
}

function formatDate(dateAsStr) {
	if(dateAsStr == null) {
		return '';
	}
	var dateAsNum = getNumber(dateAsStr, 0);
	if(dateAsNum == 0) {
		return '';
	}
	var dateToDisplay = moment(dateAsNum).format('YYYY-MM-DD');
	return dateToDisplay;
}