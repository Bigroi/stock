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
	if (formContainer.find("form")[0].checkValidity()){
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
		return false;
	} else {
		return true;
	}
};

function processRequestResult(answer, messageDiv){
	if (answer.result > 0){
		messageDiv.css("background-color", "green");
	} else if (answer.result < 0){
		messageDiv.css("background-color", "red");
	} else {
		document.location = answer.data;
		return 0;
	}
	messageDiv.text(answer.data);
	return answer.result;
}

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

function setLotDialogPlugin(element, id){
	var buttons = [{
		text:"Save",
		id:"save",
		url:"/lot/json/Save.spr",
		close:true 
	},
	{
		text:"start-trading",
		id:"start-trading",
		url:"/lot/json/StartTrading.spr",
		close:true
	},
	{
		text:"save-start-trading",
		id:"save-start-trading",
		url:"/lot/json/SaveAndActivate.spr",
		close:true
	},
	{
		text:"Delete",
		id:"delete",
		url:"/lot/json/Cancel.spr",
		close:true 
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
			} else {
				$("#save-start-trading").remove();
			}
			
			if (lot.status != "INACTIVE"){
				$("#start-trading").remove();
			} 
		},
		buttons:buttons,
	});
}

function setTenderDialogPlugin(element, id){
	var buttons = [{
		text:"Save",
		id:"save",
		url:"/tender/json/Save.spr",
		close:true 
	},
	{
		text:"start-trading",
		id:"start-trading",
		url:"/tender/json/StartTrading.spr",
		close:true
	},
	{
		text:"save-start-trading",
		id:"save-start-trading",
		url:"/tender/json/SaveAndActivate.spr",
		close:true
	},
	{
		text:"Delete",
		id:"delete",
		url:"/tender/json/Cancel.spr",
		close:true 
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
		afterLoad:function(tender){
			if (tender.id < 0){
				$("#delete").remove();
				$("#start-trading").remove();
			} else {
				$("#save-start-trading").remove();
			}
			if (tender.status != "INACTIVE"){
				$("#start-trading").remove();
			}
		},
		buttons:buttons,
	});
}

function setProductDialogPlugin(element, id){
	var buttons = [{
		text:"Save",
		id:"save",
		url:"/product/json/admin/Save.spr",
		close:true 
	},
	{
		text:"Delete",
		id:"delete",
		url:"/product/json/admin/Delete.spr",
		close:true 
	},
	{
		text:"Cancel",
		id:"cancel",
		close:true
	}];
	
	element.dialogbox({
		container:$("#product-form-container"),
		formUrl:"/product/admin/Form.spr", 
		formParams:{id:id},
		formData:"/product/json/admin/Form.spr",
		height:"60%",
		afterLoad:function(product){
			if (product.id < 0){
				$("#delete").remove();
			}
		},
		buttons:buttons,
	});
}

function sendDealFormData(formContainer, url, id){
	sendFormData(formContainer, 
			url, 
			function(answer){
				processRequestResult(answer, $('.form-message'));
				setFormData($('#form-container'), '/deal/json/Form.spr', {id:id}, function(){
					$("#approve").remove();
					$('#reject').remove();
				})
			}); 
}

function initDealForm(formContainer, url, id){
	setFormData(formContainer, url, {id:id}, function(deal){
		if (deal.status != 'ON_APPROVE'){
			$("#approve").remove();
			$('#reject').remove();
		}
		$.getScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap");
	})
}