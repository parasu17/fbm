var tabulator;
var clients;

function clientsPage() {
	clearPage();
	createClientsPage();
}

function createClientsPage() {
	createButtons();
	tabulator = createClientsTable();
	refreshData();
}

function createButtons() {
	var parent = getRoot();
	
	var buttonTableStr = 
		'<table class="buttonTableClass">' +
			'<tr>' +
				'<td>' +
					'<button type="button" onclick="openAddClientForm()">Add Client</button>' +
				'</td>' +
				'<td>' +
					'<button type="button" onclick="deleteClients()">Delete Client</button>' +
				'</td>' +
				'<td>' +
					'<button type="button" onclick="refreshData()">Refresh Clients</button>' +
				'</td>' +
			'</tr>' +
		'</table>';

	parent.appendChild(createDomElement(buttonTableStr));
}

function createClientsTable() {
	var parent = getRoot();
	var clientsTableDiv = createClientsTableDomElement();
	parent.appendChild(clientsTableDiv);

	var cols = getClientsTableColumns();
	//create Tabulator on DOM element with id "clients-table"
	 var table = new Tabulator("#clients-table", {
	  	height:"50%", // set height of table (in CSS or here), this enables the Virtual DOM and improves render speed dramatically (can be any valid css height value)
	  	layout:"fitColumns", //fit columns to width of table (optional)
	  	columns:cols,
	  	selectable:true,
	    pagination:"local",
	    paginationSize:20,
	  	selectableRangeMode:"click"
	 });
	 
	 return table;
}

function createClientsTableDomElement() {
	return createDomElement('<div id="clients-table"></div>');
}

function getClientsTableColumns() {
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
 	 				{  title:"Name"     , field:"client_name" , width:150     },
 	 				{  title:"Address"  , field:"address"     , align:"left"  },
 	 				{  title:"City"     , field:"city"        , align: "left" },
 	 				{  title:"Province" , field:"province"    , align:"left"  },
 	 				{  title:"PinCode"  , field:"pin"         , align:"left"  },
 	 				{  title:"Country"  , field:"country"     , align:"left"  }
 	 			];
	
  	return columns;
}

function openAddClientForm() {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	createCustomAlert('Add Client', createDomElement(this.responseText));
	    }
	  };
	  xhttp.open("GET", "templates/addClient.txt", true);
	  xhttp.send();
}

function closeAddClientForm() {
	document.getElementById("addNewClientForm").style.display = "none";
}
	


////////////////////////////////////////////////////////////////////////////////////////
//                                                                                    //
//                                 CRUD OPERATIONS                                    //
//                                                                                    //
////////////////////////////////////////////////////////////////////////////////////////

// data handling
function refreshData() {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	clients = JSON.parse(this.responseText);
	    	tabulator.setData(clients);
	    }
	  };
	  xhttp.open("GET", "services/clients/getAllClients", true);
	  xhttp.send();
}

function addClient() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var client = JSON.parse(this.responseText);
			// tabulator.setData(clients);
			tabulator.addRow(client);
		}
	};
	xhttp.open("POST", "services/clients/addClient", true);
	xhttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
	var idList = [
					'addClient_client_name' , 'addClient_address' , 'addClient_city',
					'addClient_province'    , 'addClient_pin'     , 'addClient_country'
				];
	var newClient = getNewEntityJson(idList);
	xhttp.send(JSON.stringify(newClient));
}

function deleteClients() {
	var selectedRows = tabulator.getSelectedRows();
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			for(var i in selectedRows) {
				selectedRows[i].delete();
			}
		}
	};
	xhttp.open("POST", "services/clients/deleteClients", true);
	xhttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
	xhttp.send(JSON.stringify(tabulator.getSelectedData()));
}