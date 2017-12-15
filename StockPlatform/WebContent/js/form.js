function getFormData(formContainer){
	var  data = {};
	var formElementNames = ["input", "select", "textarea"];
	for (var i =0; i < formElementNames.length; i++){
		var formElementName = formElementNames[i];
		var formElements = formContainer.find(formElementName);
		for(var j = 0; j < formElements.length; j++){
			var name = formElements[j].getAttribute("name");
			var value = formElements[j].value;
			data[name]=value;                   
		}
	}
	data = JSON.stringify(data,"",1);
	return data;
}

function sendFormData(formContainer, url, successFunction) {
	var data = getFormData(formContainer);
	var param;
	if (url.indexOf("Login") >= 0){
		console.log(data);
		param = JSON.parse(data);
	} else {
		param = {json:data};
	}
	$.post(url, param, function(answer){
		successFunction(answer);
	}, "json");

};

//form functions
function setLoginDialogPlagin(element){
	element.dialogbox({
		container:$("#login-form-container"),
		width:"70%",
		height:"70%",
		formUrl:"/Login.spr", 
		buttons:[
		{
			text:"Login",
			url:"/access/Login.spr",
			close:true 
		},
		{
			text:"Cancel",
			close:true
		}], 
						
	});
}