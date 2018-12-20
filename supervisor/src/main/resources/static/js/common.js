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