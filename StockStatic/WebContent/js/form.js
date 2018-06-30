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

function sendFormData(formContainer, submitFunction, $dialogbox, login, closeOnClick) {
	if (formContainer[0].checkValidity()){
		var data = getFormData(formContainer);
		
		var param;
		if (login){
			param = JSON.parse(data);
		} else {
			param = {json:data};
		}
		submitFunction(formContainer, param, $dialogbox, closeOnClick);
		
		return false;
	} else {
		return true;
	}
};

function processRequestResult(formContainer, answer, messageDiv){
	if (answer.result > 0){
		messageDiv.css("background-color", "green");
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

function updateTable($table, model, idColumnValue, object){
	if (idColumnValue == "" || idColumnValue == -1){
		$table.DataTable().row.add(object).draw(false);
	} else {
		$table.DataTable().rows().every( function () {
		    var d = this.data();
		    if (d[model.idColumn] == idColumnValue){
		    	this.data(object).draw(false);
		    	return;
		    }
		} );
	}
}

//form functions
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

function getLoginDialogParams(){
	return {
		hasCloseButton  : true,
		hasCloseOverlay : true,
		container:$("#form-container"),
		formUrl:getContextRoot() + "/Login.spr", 
		buttons:[
		{
			text: l10n.translate("label.button.login"),
			id:"login",
			submit:loginCallback,
			login:true
		}], 				
	};
	
	function loginCallback(formContainer, params){
		$.post(getContextRoot() + "/account/json/Login.spr", params, function(answer){
			answer = JSON.parse(answer);
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
		});
	}
}

function getAddressDialogParams(table, model, id){
	return {
		hasCloseButton  : true,
		hasCloseOverlay : true,
		container:$("#form-container"),
		formUrl:getContextRoot() + "/address/Form.spr", 
		formParams:{id:id},
		formData:getContextRoot() + "/address/json/Form.spr",
		buttons:[
		{
			text: l10n.translate("label.button.save"),
			id:"save",
			submit:saveCallback
		}], 				
	};
	
	function saveCallback(formContainer, params, $dialogbox){
		$.post(getContextRoot() + "/address/json/Save.spr", params, function(answer){
			var idColumnValue = params.json[model.idColumn];
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
			if (answer.result > 0){
				updateTable(table, model, idColumnValue, answer.data);
				$dialogbox.remove();
			}
		});
	}
}

function getReginDialogParams(){
	return {
		hasCloseButton  : true,
		hasCloseOverlay : true,
		container:$("#form-container"),
		formUrl:getContextRoot() + "/Registration.spr", 
		buttons:[
		{
			text: l10n.translate("label.button.finishRegistration"),
			id:"finish-registration",
			submit: reginCallback
		}], 			
	};
	
	function reginCallback(formContainer, params){
		$.post(getContextRoot() + "/account/json/Registration.spr", params, function(answer){
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
		});
	}
}

function getLotDialogParams(table, model, id){
	return {
		hasCloseButton  : true,
		hasCloseOverlay : true,
		container:$("#form-container"),
		formUrl:getContextRoot() + "/lot/Form.spr", 
		formParams:{id:id},
		formData:getContextRoot() + "/lot/json/Form.spr",
		height:"80%",
		buttons: [{
				text: l10n.translate("label.button.save"),
				id:"save",
				submit: lotSaveCallback
			},
			{
				text: l10n.translate("label.button.save_start_trading"),
				id:"save-start-trading",
				submit: lotActivateCallback
			}],
	};
	
	function lotActivateCallback(formContainer, params, $dialogbox){
		var idColumnValue = JSON.parse(params.json)[model.idColumn];
		$.post(getContextRoot() + "/lot/json/SaveAndActivate.spr", params, function(answer){
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
			if (answer.result > 0){
				updateTable(table, model, id, answer.data);
				$dialogbox.remove();
			}
		});
	}
	
	function lotSaveCallback(formContainer, params, $dialogbox){
		var idColumnValue = JSON.parse(params.json)[model.idColumn];
		$.post(getContextRoot() + "/lot/json/Save.spr", params, function(answer){
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
			if (answer.result > 0){
				updateTable(table, model, idColumnValue, answer.data);
				$dialogbox.remove();
			}
		});
	}
}

function getAccountDialogParams(model, id){
	return {
		hasCloseButton  : true,
		hasCloseOverlay : true,
		container:$("#form-container"),
		formUrl:getContextRoot() + "/account/Form.spr", 
		formParams:{id:id},
		formData:getContextRoot() + "/account/json/Form.spr",
		height:"80%",
		buttons: [{
				text: l10n.translate("label.button.save"),
				id:"save",
				submit: saveCallback
			}],
	};
	
	function saveCallback(formContainer, params, $dialogbox){
		$.post(getContextRoot() + "/account/json/Save.spr", params, function(answer){
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
		});
	}
}

function getContactUsDialogParams(){
	return {
		hasCloseButton  : true,
		hasCloseOverlay : true,
		container:$("#message-dialog-container"),
		formUrl:getContextRoot() + "/feedback/Form.spr", 
		formData:getContextRoot() + "/feedback/json/Form.spr", 
		height:"15%",
		buttons:[{
			text: l10n.translate("label.button.send"),
			id:"invite",
			submit: contactUsCallback
		}],
	};
	
	function contactUsCallback(formContainer, params, $dialogbox){
		$.post(getContextRoot() + "/feedback/json/Save.spr", params, function(answer){
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
		});
	}
}

function getMessageDialogParams(message, type, action){
	return {
		container:$("#message-dialog-container"),
		formUrl:getContextRoot() + "/Message.spr", 
		formParams:{message:message,type:type},
		height:"40%",
		"hasCloseButton":true,
		"hasCloseOverlay":true,
		buttons:[{
				text: l10n.translate("label.button.ok"),
				id:"ok",
				submit: callback
			}]
	};
	
	function callback(formContainer, params, $dialogbox){
		$dialogbox.remove();
		if (action){
			action();
		}
	}
}

function getTenderDialogParams(table, model, id){
	return{
		hasCloseButton  : true,
		hasCloseOverlay : true,
		container:$("#form-container"),
		formUrl:getContextRoot() + "/tender/Form.spr", 
		formParams:{id:id},
		formData:getContextRoot() + "/tender/json/Form.spr",
		height:"80%",
		buttons:[{
			text: l10n.translate("label.button.save"),
			id:"save",
			submit: tenderSaveCallback
		},
		{
			text: l10n.translate("label.button.save_start_trading"),
			id:"save-start-trading",
			submit:tenderActivateCallback
		}],
	};
	
	function tenderSaveCallback(formContainer, params, $dialogbox){
		var idColumnValue = JSON.parse(params.json)[model.idColumn];
		$.post(getContextRoot() + "/tender/json/Save.spr", params, function(answer){
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
			if (answer.result > 0){
				updateTable(table, model, idColumnValue, answer.data);
				$dialogbox.remove();
			}
		});
	}
	
	function tenderActivateCallback(formContainer, params, $dialogbox){
		var idColumnValue = JSON.parse(params.json)[model.idColumn];
		$.post(getContextRoot() + "/tender/json/SaveAndActivate.spr", params, function(answer){
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
			if (answer.result > 0){
				updateTable(table, model, idColumnValue, answer.data);
				$dialogbox.remove();
			}
		});
	}
}

function getProductDialogParams(table, model, id){
	return {
		hasCloseButton  : true,
		hasCloseOverlay : true,
		container:$("#form-container"),
		formUrl:getContextRoot() + "/product/admin/Form.spr", 
		formParams:{id:id},
		formData:getContextRoot() + "/product/json/admin/Form.spr",
		height:"60%",
		afterLoad:function(product){
			if (product.id < 0){
				$("#delete").remove();
			}
		},
		buttons:[{
			text: l10n.translate("label.button.save"),
			id:"save",
			submit: productSaveCallback
		},
		{
			text: l10n.translate("label.button.delete"),
			id:"delete",
			submit: productDeleteCallback
		}]
	};
	
	function productSaveCallback(formContainer, params, $dialogbox){
		var idColumnValue = JSON.parse(params.json)[model.idColumn];
		$.post(getContextRoot() + "/product/json/admin/Save.spr", params, function(answer){
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
			if (answer.result > 0){
				updateTable(table, model, idColumnValue, answer.data);
				$dialogbox.remove();
			}
		});
	}
	function productDeleteCallback(formContainer, params, $dialogbox){
		var idColumnValue = JSON.parse(params.json)[model.idColumn];
		$.post(getContextRoot() + "/product/json/admin/Delete.spr", params, function(answer){
			processRequestResult(formContainer, answer, $('.dialogbox-message'));
			if (answer.result > 0){
				updateTable(table, model, idColumnValue, answer.data);
				$dialogbox.remove();
			}
		});
	}
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