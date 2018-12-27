var loggedInUser;

function isLoginFormGood() {
	var userName = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	if(userName == null || userName.length == 0) {
		return false;
	}
	if(password == null || password.length == 0) {
		return false;
	}
	return true;
}

function setLoginWarning(str) {
	var loginStatusDiv = document.getElementById('loginStatusId');
	if(loginStatusDiv == null) {
		return;
	}
	loginStatusDiv.innerHTML = str;
	showElement(loginStatusDiv);
}

function clearLoginWarning() {
	var loginStatusDiv = document.getElementById('loginStatusId');
	if(loginStatusDiv != null && (typeof loginStatusDiv.innerHTML === 'defined')) {
		loginStatusDiv.innerHTML = '';
		hideElement(loginStatusDiv);
	}
}

function hideLoginPanel() {
	var loginPanel = document.getElementById('loginPanelId');
	hideElement(loginPanel);
}

function showPageHeader() {
	var headerPanel = document.getElementById('pageHeaderId');
	showElement(headerPanel);
}

function login() {
	if(!isLoginFormGood()) {
		setLoginWarning('The username or password cannot be null or empty');
		return;
	}
	
	clearLoginWarning();

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var fbmResponse = JSON.parse(this.responseText);
	    	if(fbmResponse.success) {
	    		hideLoginPanel();
				showPageHeader();
				loggedInUser = fbmResponse.responseData;
	    	} else {
	    		setLoginWarning(fbmResponse.failureMessage);
	    	}
		}
	};
	xhttp.open("POST", "user/login", true);
	xhttp.setRequestHeader("Accept", "application/json; charset=utf-8");
	xhttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
	var body = {};
	body.username = document.getElementById('username').value;
	body.password = document.getElementById('password').value;
	var bodyJsonStr = JSON.stringify(body);
	xhttp.send(bodyJsonStr);
}