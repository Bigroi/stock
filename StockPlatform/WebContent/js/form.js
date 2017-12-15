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
	
	//XXX fix for Login to redo
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

function setFormData(formContainer, url, params, afterLoad){
	$.post(url, params, function(answer){
		var formElementNames = ["input", "select", "textarea"];
		for (var i =0; i < formElementNames.length; i++){
			var formElementName = formElementNames[i];
			var formElements = formContainer.find(formElementName);
			for(var j = 0; j < formElements.length; j++){
				var name = formElements[j].getAttribute("name");
				if (answer.data[name]){
					formElements[j].value = answer.data[name];
				}
			}
		}
		if (afterLoad){
			afterLoad(answer.data);
		}
	}, "json");
}

//form functions
function setLoginDialogPlugin(element){
	element.dialogbox({
		container:$("#login-form-container"),
		formUrl:"/Login.spr", 
		buttons:[
		{
			text:"Login",
			id:"login",
			url:"/access/Login.spr",
			close:true 
		},
		{
			text:"Cancel",
			id:"cancel",
			close:true
		}], 
						
	});
}

function setLotDialogPlugin(element){
	var buttons = [{
		text:"Save",
		id:"save",
		url:"/lot/json/Save.spr",
		close:false 
	},
	{
		text:"start-trading",
		id:"start-trading",
		url:"/lot/json/StartTrading.spr",
		close:false
	},
	{
		text:"Delete",
		id:"delete",
		url:"/lot/json/Cancel.spr",
		close:false 
	},
	{
		text:"Cancel",
		id:"cancel",
		close:true
	}];
	
	element.dialogbox({
		container:$("#lot-form-container"),
		formUrl:"/lot/Form.spr", 
		formParams:{id:id},
		formData:"/lot/json/Form.spr",
		height:"80%",
		afterLoad:function(lot){
			if (lot.id < 0){
				$("#delete").remove();
				$("#start-trading").remove();
			} else if (lot.status != "DRAFT"){
				$("#start-trading").remove();
			}
		},
		buttons:buttons,
	});
}

function setTenderDialogPlugin(element){
	var buttons = [{
		text:"Save",
		id:"save",
		url:"/tender/json/Save.spr",
		close:false 
	},
	{
		text:"start-trading",
		id:"start-trading",
		url:"/tender/json/StartTrading.spr",
		close:false
	},
	{
		text:"Delete",
		id:"delete",
		url:"/tender/json/Cancel.spr",
		close:false 
	},
	{
		text:"Cancel",
		id:"cancel",
		close:true
	}];
	
	element.dialogbox({
		container:$("#tender-form-container"),
		formUrl:"/tender/Form.spr", 
		formParams:{id:id},
		formData:"/tender/json/Form.spr",
		height:"80%",
		afterLoad:function(lot){
			if (lot.id < 0){
				$("#delete").remove();
				$("#start-trading").remove();
			} else if (lot.status != "DRAFT"){
				$("#start-trading").remove();
			}
		},
		buttons:buttons,
	});
}