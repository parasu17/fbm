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

function formatQueryParams( params ){
	  return "?" + Object
	        .keys(params)
	        .map(function(key) {
	          return key + "=" + encodeURIComponent(params[key])
	        })
	        .join("&")
}

function getGeoLocation(positionCallbackFn, errorCallbackFn) {
	if (navigator.geolocation) {
		var clickedTime = (new Date()).getTime(); //get the current time
		var geoLocTimeout = 10; //reset the timeout just in case you call it more then once
        ensurePosition(positionCallbackFn, errorCallbackFn, clickedTime, geoLocTimeout); //call recursive function to get position
		//navigator.geolocation.getCurrentPosition(showPosition);
	} else {
		errorCallbackFn("Geolocation is not supported by this browser.");
	}
}

// recursive position function
function ensurePosition(callback, errorCallback, timestamp, geoLocTimeout) {
	if (geoLocTimeout < 6000) { // set at what point you want to just give up
		// call the geolocation function
		navigator.geolocation.getCurrentPosition(function(position) { // on success
			// if the timestamp that is returned minus the time that was set
			// when called is greater then 0 the position is up to date
			if (position.timestamp - timestamp >= 0) {
				// GPSTimeout = 10; // reset timeout just in case
				callback(position); // call the callback function you created
			} else { // the gps that was returned is not current and needs to be refreshed
				// GPSTimeout += GPSTimeout; // increase the timeout by itself n*2
				ensurePosition(callback, errorCallback, timestamp, geoLocTimeout * 2); // call itself to refresh
			}
		}, function() { // error: gps failed so we will try again
			// GPSTimeout += GPSTimeout; // increase the timeout by itself n*2
			ensurePosition(callback, errorCallback, timestamp, geoLocTimeout * 2);// call itself to try again
		}, {
			maximumAge : 0,
			timeout : geoLocTimeout
		})
	} else {
		errorCallback("Geolocation could not be found in this browser.");
	}
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

function getAllClientsWithCleaningTypes(lat, lng, clientListCallback) {
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
	  
	  var endPointUrl = "services/clients/getAllClientsWithCleaningTypes"; 
	  
	  if(lat && lng) {
		  var queryParams = {};
		  queryParams.latitude = lat;
		  queryParams.longitude = lng;
		  endPointUrl += formatQueryParams(queryParams);
	  }
	  
	  xhttp.open("GET", endPointUrl, true);
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
