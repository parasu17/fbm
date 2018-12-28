var tabulator;
var clients;


//common utility methods
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


function clearPage() {
	var myNode = document.getElementById("fbmData");
	myNode.innerHTML = '';
}

function getRoot() {
	return document.getElementById('fbmData');
}

function getInputElements(idBeginStr) {
	return document.querySelectorAll('*[id^="' + idBeginStr + '"]');
}

function getAddEntityJson(id) {
	var inputs = getInputElements(id);
	var jsonIn = {};
	for( var i in inputs) {
		if (typeof inputs[i].localName === "undefined") {
			continue;
		}
		if(inputs[i].localName == 'input') {
			jsonIn[inputs[i].name] = inputs[i].value;
		}
	}
	return jsonIn;
}

function getNewEntityJson(idList) {
	var jsonIn = {};
	for(var i in idList) {
		var inEle = document.getElementById(idList[i]);
		jsonIn[inEle.name] = inEle.value;
	}
	return jsonIn;
}

function createDomElement(str) {
	var divEle = document.createElement('tmpDiv');
	divEle.innerHTML = str;
	return divEle.firstChild;
}

function getAllClientsWithCleaningTypes(clientListCallback) {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var fbmResponse = JSON.parse(this.responseText);
	    	if(fbmResponse.success) {
	    		clientListCallback(fbmResponse.responseData);
	    	} else {
	    		showAlert('Could not fetch all clients from server');
	    	}
	    }
	  };
	  xhttp.open("GET", "services/clients/getAllClientsWithCleaningTypes", true);
	  xhttp.send();
}

function showElement(element) {
	if(element == null) {
		return;
	}
	if((typeof element.style === "undefined") || (typeof element.style.display === "undefined")) {
		return;
	}

	element.style.display = "block";
}

function hideElement(element) {
	if(element == null) {
		return;
	}
	if((typeof element.style === "undefined") || (typeof element.style.display === "undefined")) {
		return;
	}

	element.style.display = "none";
}

function getLoggedInUser() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var fbmResponse = JSON.parse(this.responseText);
	    	if(fbmResponse.success) {
				loggedInUser = fbmResponse.responseData;
				showElement(document.getElementById('pageHeaderId'));
	    	}
		}
	};
	xhttp.open("POST", "services/user/getLoggedInUser", true);
	xhttp.setRequestHeader("Accept", "application/json; charset=utf-8");
	xhttp.send();
}

function showAlert(txt) {
	alert(txt);
}
