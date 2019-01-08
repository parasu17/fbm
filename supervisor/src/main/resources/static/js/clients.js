

function clientsPage() {
	clearPage();
	createClientsPage();
}

function createClientsPage() {
	createButtons();
	tabulator = createClientsTable();
	getAllClientsWithCleaningTypes(null, null, refreshClients);
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
					'<button type="button" onclick="getAllClientsWithCleaningTypes(refreshClients)">Refresh Clients</button>' +
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
	    	prepareClientTypesForAddClientForm('Add Client', createDomElement(this.responseText));
	    }
	  };
	  xhttp.open("GET", "templates/addClient.txt", true);
	  xhttp.send();
}

function prepareClientTypesForAddClientForm(title, dataElement) {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var table = dataElement.getElementsByTagName('table')[0];
	    	var row = table.insertRow(1);
	    	row.innerHTML = getClientTypeRow(JSON.parse(this.responseText));
	    	createCustomAlert(title, dataElement);
	    }
	  };
	  xhttp.open("GET", "services/clients/getAllCts", true);
	  xhttp.send();
}

function getClientTypeRow(clientTypes) {
	/*
	 <select>
		  <option value="volvo">Volvo</option>
		  <option value="saab">Saab</option>
		  <option value="mercedes">Mercedes</option>
		  <option value="audi">Audi</option>
	</select>
	 */
	if(clientTypes == null || clientTypes.length == 0) {
		return;
	}

	var select = '<td><label for="addClient_clientType"><b>Client Type</b></label></td>' +
					'<td><div><select id="addClient_clientType" name="cleaning_type_id">';
	for(i in clientTypes) {
		select = select + '<option value="' + clientTypes[i].id + '">' + clientTypes[i].name + '</option>';
	}
	select = select + '</select></div></td>';
	return select;
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
function refreshClients(clientList) {
	clients = clientList;
	tabulator.setData(clients);
}

function addClient() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
	    	var fbmResponse = JSON.parse(this.responseText);
	    	if(fbmResponse.success) {
				var client = fbmResponse.responseData;
				tabulator.addRow(client);
				removeCustomAlert();
				var dataElement = null;
				showAlert('Successfully added client=' + client.client_name);
	    	} else {
	    		var failMsg = (fbmResponse.failureMessage && fbmResponse.failureMessage.length > 0) ? ': ' + fbmResponse.failureMessage : '';
	    		showAlert('Could not add client. Please check the data' + failMsg);
	    	}
		} else if (this.readyState == 4) {
			showAlert('Could not add client. Please check the data');
		}
	};
	xhttp.open("POST", "services/clients/addClient", true);
	xhttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
	var idList = [
					'addClient_client_name' , 'addClient_clientType', 'addClient_address' , 'addClient_city',
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
	    	var fbmResponse = JSON.parse(this.responseText);
	    	if(fbmResponse.success) {
				for(var i in selectedRows) {
					selectedRows[i].delete();
				}
	    	}
		}
	};
	xhttp.open("POST", "services/clients/deleteClients", true);
	xhttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
	xhttp.send(JSON.stringify(tabulator.getSelectedData()));
}