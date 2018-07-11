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
	if ($('#deal-form').length > 0){
		var dealForm = $('#deal-form');
		initDealForm(dealForm, dealForm.attr("data-url"), dealForm.attr("data-id"));
	}
});

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

function initDealForm(formContainer, url, id){
	$.post(url, {id:id}, function(answer){
		setFormInputs(formContainer, answer.data);
		if (answer.data.statusCode != 'ON_APPROVE'){
			$(".deal-button").attr("style", "display:none");
		}
		$.getScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initDealMap");
	}, "json");
}

function sendResetFormData(formContainer, url){
	return sendFormData(
			$('#login-form'),
			{
				submitUrl: getContextRoot() + '/account/json/ResetPassword.spr',
				submitFunction: simpleButtonCallback
			}); 
}

function sendDealFormData(url){
	return sendFormData(
			$("#deal-form"),
			{
				submitUrl: getContextRoot() + url,
				submitFunction: callback
			});
	
	function callback(buttonDef, params, formContainer){
		$.post(buttonDef.submitUrl, params, function(answer){
			processRequestResult(formContainer, answer, $('.form-message'));
			if (answer.result > 0){
				$('.deal-button').attr("style", "display:none");
			}
		});
	} 
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