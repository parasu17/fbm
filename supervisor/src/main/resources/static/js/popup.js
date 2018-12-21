
function createCustomAlert(txt, dataElement) {
    d = document;

    if(d.getElementById("modalContainer")) return;

    mObj = d.getElementsByTagName("body")[0].appendChild(d.createElement("div"));
    mObj.id = "modalContainer";
    mObj.style.height = d.documentElement.scrollHeight + "px";

    alertObj = mObj.appendChild(d.createElement("div"));
    alertObj.id = "alertBox";
    if(d.all && !window.opera) alertObj.style.top = document.documentElement.scrollTop + "px";
    alertObj.style.left = (d.documentElement.scrollWidth - alertObj.offsetWidth)/2 + "px";
    alertObj.style.visiblity="visible";

    h1 = alertObj.appendChild(d.createElement("h1"));
    h1.appendChild(d.createTextNode(txt));

    if(dataElement) {
    	alertObj.appendChild(dataElement);
    } else {
    	alertObj.appendChild(getOkButton());
    }

    alertObj.style.display = "block";

}

function getOkButton() {
	var butTxt = '<div class="add-client-button-panel"><button type="button" onclick="removeCustomAlert()">OK</button></div>';
	return createDomElement(butTxt);
}

function removeCustomAlert() {
    document.getElementsByTagName("body")[0].removeChild(document.getElementById("modalContainer"));
}