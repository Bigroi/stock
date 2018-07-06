$(document).ready(function(){
	$(".registration-button").on("click", function(){
		showDialog(getReginDialogParams());
	});
	$(".login-button").on("click", function(){
		showDialog(getLoginDialogParams());
	});
	$('.send-message').on("click", function(){
		showDialog(getContactUsDialogParams());
	});
	$('.edit-account').on("click", function(){
		showDialog(getAccountDialogParams());
	});
});

function getFormData(formContainer){
	var  data = {};
	var formElementNames = ["input", "select", "textarea"];
	for (var i =0; i < formElementNames.length; i++){
		var formElementName = formElementNames[i];
		var formElements = formContainer.find(formElementName);
		for(var j = 0; j < formElements.length; j++){
			var name = formElements[j].getAttribute("name");
			var value = formElements[j].value;
			addToResult(data, name, value);
		}
	}
	data = JSON.stringify(data,"",1);
	return data;
	
	function addToResult(toObject, name, value){
		var dotIndex = name.indexOf(".");
		if (dotIndex < 0){
			toObject[name] = value;
		} else {
			var subObjectName = name.substr(0, dotIndex);
			var subObject = toObject[subObjectName];
			name = name.substr(dotIndex + 1);
			if (!subObject){
				subObject = {};
				toObject[subObjectName] = subObject;
			}
			addToResult(subObject, name, value);
		}
	}
}

function sendFormData(formContainer, buttonDef, $dialogbox) {
	if (formContainer[0].checkValidity()){
		var data = getFormData(formContainer);
		
		var param;
		if (buttonDef.login){
			param = JSON.parse(data);
		} else {
			param = {json:data};
		}
		
		buttonDef.submitFunction(
				buttonDef,
				param,
				formContainer, 
				$dialogbox);
		
		return false;
	} else {
		return true;
	}
};

function setFormData(formContainer, url, params, afterLoad){
	$.post(url, params, function(answer){
		setFormInputs(formContainer, answer.data);
		if (afterLoad){
			afterLoad(answer.data);
		}
	}, "json");
}

function setFormInputs(formContainer, object){
	var formElementNames = ["input", "select", "textarea"];
	for (var i =0; i < formElementNames.length; i++){
		var formElementName = formElementNames[i];
		var formElements = formContainer.find(formElementName);
		for(var j = 0; j < formElements.length; j++){
			var name = formElements[j].getAttribute("name");
			var value = getValue(object, name);
			formElements[j].value = value;
		}
	}
	
	function getValue(object, name){
		var dotIndex = name.indexOf(".");
		if (dotIndex < 0){
			if  (object[name]){
				return object[name];
			} else {
				return "";
			}
		} else {
			var subObjectName = name.substr(0, dotIndex);
			var subObject = object[subObjectName];
			if (!subObject){
				return "";
			}
			name = name.substr(dotIndex + 1);
			return getValue(subObject, name);
		}
	}
}

function processRequestResult(formContainer, answer, messageDiv){
	if (answer.result > 0){
		messageDiv.addClass("success-message");
		setFormInputs(formContainer, answer.data);
	} else if (answer.result < 0){
		messageDiv.addClass("error-message");
	} else {
		document.location = answer.data;
		return 0;
	}
	messageDiv.text(l10n.translate(answer.message));
	if (messageDiv.length > 0){
		messageDiv[0].scrollIntoView(true);
	}
	return answer.result;
}

function simpleButtonCallback(buttonDef, params, formContainer, $dialogbox){
	$.post(buttonDef.submitUrl, params, function(answer){
		if (typeof answer == 'string'){
			answer = JSON.parse(answer);
		}
		processRequestResult(formContainer, answer, $('.dialogbox-message'));
		if (answer.result > 0 && $dialogbox){
			$dialogbox.remove();
		}
	});
}

function buttonCallbackWithTableUpdate(buttonDef, params, formContainer, $dialogbox){
	$.post(buttonDef.submitUrl, params, function(answer){
		if (typeof answer == 'string'){
			answer = JSON.parse(answer);
		}
		var idColumnValue = JSON.parse(params.json)[buttonDef.model.idColumn];
		processRequestResult(formContainer, answer, $('.dialogbox-message'));
		if (answer.result > 0){
			updateTable(idColumnValue, answer.data);
			$dialogbox.remove();
		}
		
		function updateTable(idColumnValue, object){
			if (idColumnValue == "" || idColumnValue == -1){
				buttonDef.table.DataTable().row.add(object).draw(false);
			} else {
				buttonDef.table.DataTable().rows().every( function () {
				    var d = this.data();
				    if (d[buttonDef.model.idColumn] == idColumnValue){
				    	this.data(object).draw(false);
				    	return;
				    }
				} );
			}
		}
	});
}

//form functions
function getLoginDialogParams(){
	return {
		formUrl:"/Login.spr", 
		buttons:[
		{
			text: l10n.translate("label.button.login"),
			id:"login",
			submitUrl: "/account/json/Login.spr",
			login:true
		}], 				
	};
}

function getAddressDialogParams(id, $table, model){
	return {
		formUrl:"/address/Form.spr", 
		formParams:{id:id},
		formData:"/address/json/Form.spr",
		buttons:[
		{
			text: l10n.translate("label.button.save"),
			id: "save",
			submitUrl: "/address/json/Save.spr",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		}]
	};
}

function getReginDialogParams(){
	return {
		formUrl:"/Registration.spr", 
		buttons:[
		{
			text: l10n.translate("label.button.finishRegistration"),
			id: "finish-registration",
			submitUrl : "/account/json/Registration.spr"
		}]			
	};
}

function getLotDialogParams(id, $table, model){
	return {
		formUrl: "/lot/Form.spr", 
		formParams:{id:id},
		formData: "/lot/json/Form.spr",
		buttons: [{
				text: l10n.translate("label.button.save"),
				id:"save",
				submitUrl: "/lot/json/Save.spr",
				submitFunction: buttonCallbackWithTableUpdate,
				table : $table, 
				model : model 
			},
			{
				text: l10n.translate("label.button.save_start_trading"),
				id:"save-start-trading",
				submitUrl: "/lot/json/SaveAndActivate.spr",
				submitFunction: buttonCallbackWithTableUpdate,
				table : $table, 
				model : model
			}]
	};	
}

function getAccountDialogParams(id){
	return {
		formUrl: "/account/Form.spr", 
		formParams:{id:id},
		formData: "/account/json/Form.spr",
		buttons: [{
				text: l10n.translate("label.button.save"),
				id:"save",
				submitUrl: "/account/json/Save.spr"
			}]
	};	
}

function getContactUsDialogParams(){
	return {
		container:$("#message-dialog-container"),
		formUrl: "/feedback/Form.spr", 
		formData: "/feedback/json/Form.spr", 
		buttons:[{
			text: l10n.translate("label.button.send"),
			id:"contact-us",
			submitUrl: "/feedback/json/Save.spr"
		}]
	};
}

function getMessageDialogParams(message, type, action){
	return {
		container:$("#message-dialog-container"),
		formUrl:"/Message.spr", 
		formParams:{message:message,type:type},
		buttons:[{
				text: l10n.translate("label.button.ok"),
				id:"ok",
				submitFunction: callback
			}]
	};
	
	function callback(url, params, formContainer, $dialogbox){
		$dialogbox.remove();
		if (action){
			action();
		}
	}
}

function getTenderDialogParams(id, $table, model){
	return{
		formUrl: "/tender/Form.spr", 
		formParams:{id:id},
		formData: "/tender/json/Form.spr",
		buttons:[{
			text: l10n.translate("label.button.save"),
			id:"save",
			submitUrl: "/tender/json/SaveAndActivate.spr",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		},
		{
			text: l10n.translate("label.button.save_start_trading"),
			id:"save-start-trading",
			submitUrl: "/tender/json/SaveAndActivate.spr",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		}],
	};
}

function getProductDialogParams(id, $table, model){
	return {
		formUrl: "/product/admin/Form.spr", 
		formParams:{id:id},
		formData: "/product/json/admin/Form.spr",
		buttons:[{
			text: l10n.translate("label.button.save"),
			id:"save",
			submitUrl: "/product/json/admin/Save.spr",
			submitFunction: productSaveCallback,
			table : $table, 
			model : model
		},
		{
			text: l10n.translate("label.button.delete"),
			id:"delete",
			submitUrl: "/product/json/admin/Delete.spr",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		}]
	};
}




function sendDealFormData(formContainer, url){
	return sendFormData(formContainer, function (formContainer, params){
				$.post(url, params, function(answer){
					processRequestResult(formContainer, answer, $('.form-message'));
					if (answer.result > 0){
						$('#approve-button').attr("style", "display:none");
						$('#reject-button').attr("style", "display:none");
						$('#transport-button').attr("style", "display:none");
					}
				});
			}); 
}

function initDealForm(formContainer, url, id){
	setFormData(formContainer, url, {id:id}, function(deal){
		if (deal.statusCode != 'ON_APPROVE'){
			$("#approve-button").attr("style", "display:none");
			$('#reject-button').attr("style", "display:none");
			$('#transport-button').attr("style", "display:none");
		}
		if (!$("#seller-foto")[0].value){
			$("#seller-foto").attr("style", "display:none");
		}
		$.getScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initDealMap");
	})
}

function sendResetFormData(formContainer, url){
	return sendFormData(formContainer, function (formContainer, params){
				$.post(url, params, function(answer){
					processRequestResult(formContainer, answer, $('.dialogbox-message'));
				});
			}); 
}

function openLoginForm(){
	$(".dialogbox").remove();
	showDialog(getLoginDialogParams());
}

function openRegistrationForm(){
	$(".dialogbox").remove();
	showDialog(getReginDialogParams());
}

function getContextRoot(){
	var contextRoot = $("meta[name=context-root]").attr("content");
	return contextRoot ? contextRoot : "";
	
}