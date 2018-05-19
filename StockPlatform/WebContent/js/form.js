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
	messageDiv.text(translate(answer.message));
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
function setLoginDialogPlugin(element){
	element.dialogbox({
		container:$("#login-form-container"),
		formUrl:"/Login.spr", 
		buttons:[
		{
			text: translate("label.button.login"),
			id:"login",
			submit:function(formContainer, params){
				$.post("/account/json/Login.spr", params, function(answer){
					answer = JSON.parse(answer);
					processRequestResult(formContainer, answer, $('.dialogbox-message'));
				});
			},
			login:true
		},
		{
			text: translate("label.button.reset"),
			id:"reset",
			submit:function(formContainer, params){
				$.post("/account/json/ResetPassword.spr", params, function(answer){
					answer = JSON.parse(answer);
					processRequestResult(formContainer, answer, $('.dialogbox-message'));
				});
			},
			type:"link"
		}], 
						
	});
}

function setLotDialogPlugin(element, table, model, id){
	var buttons = [{
		text: translate("label.button.save"),
		id:"save",
		submit:function(formContainer, params, $dialogbox){
			var idColumnValue = JSON.parse(params.json)[model.idColumn];
			$.post("/lot/json/Save.spr", params, function(answer){
				processRequestResult(formContainer, answer, $('.dialogbox-message'));
				if (answer.result > 0){
					updateTable(table, model, idColumnValue, answer.data);
					$dialogbox.remove();
				}
			});
		}
	},
	{
		text: translate("label.button.save_start_trading"),
		id:"save-start-trading",
		submit:function(formContainer, params, $dialogbox){
			var idColumnValue = JSON.parse(params.json)[model.idColumn];
			$.post("/lot/json/SaveAndActivate.spr", params, function(answer){
				processRequestResult(formContainer, answer, $('.dialogbox-message'));
				if (answer.result > 0){
					updateTable(table, model, id, answer.data);
					$dialogbox.remove();
				}
			});
		}
	}];
	
	element.dialogbox({
		container:$("#lot-form-container"),
		formUrl:"/lot/Form.spr", 
		formParams:{id:id},
		formData:"/lot/json/Form.spr",
		height:"80%",
		buttons:buttons,
	});
}

function setInviteDialogPlugin(element){
	
	var buttons = [{
		text: translate("label.button.invite"),
		id:"invite",
		submit:function(formContainer, params, $dialogbox){
			$.post("/account/json/AddUser.spr", params, function(answer){
				processRequestResult(formContainer, answer, $('.dialogbox-message'));
				if (answer.result > 0){
					$dialogbox.remove();
				}
			});
		}
	}];
	
	element.dialogbox({
		container:$("#invite-form-container"),
		formUrl:"/account/AddUser.spr", 
		height:"15%",
		buttons:buttons,
	});
}

function setContactUsDialogPlugin(element){
	
	var buttons = [{
		text: translate("label.button.send"),
		id:"invite",
		submit:function(formContainer, params, $dialogbox){
			$.post("/feedback/json/Save.spr", params, function(answer){
				processRequestResult(formContainer, answer, $('.dialogbox-message'));
			});
		}
	}];
	
	element.dialogbox({
		container:$("#message-dialog-container"),
		formUrl:"/feedback/Form.spr", 
		formData:"/feedback/json/Form.spr", 
		height:"15%",
		buttons:buttons,
	});
}

function setTenderDialogPlugin(element, table, model, id){
	var buttons = [{
		text: translate("label.button.save"),
		id:"save",
		submit:function(formContainer, params, $dialogbox){
			var idColumnValue = JSON.parse(params.json)[model.idColumn];
			$.post("/tender/json/Save.spr", params, function(answer){
				processRequestResult(formContainer, answer, $('.dialogbox-message'));
				if (answer.result > 0){
					updateTable(table, model, idColumnValue, answer.data);
					$dialogbox.remove();
				}
			});
		}
	},
	{
		text: translate("label.button.save_start_trading"),
		id:"save-start-trading",
		submit:function(formContainer, params, $dialogbox){
			var idColumnValue = JSON.parse(params.json)[model.idColumn];
			$.post("/tender/json/SaveAndActivate.spr", params, function(answer){
				processRequestResult(formContainer, answer, $('.dialogbox-message'));
				if (answer.result > 0){
					updateTable(table, model, idColumnValue, answer.data);
					$dialogbox.remove();
				}
			});
		}
	}];
	
	element.dialogbox({
		container:$("#tender-form-container"),
		formUrl:"/tender/Form.spr", 
		formParams:{id:id},
		formData:"/tender/json/Form.spr",
		height:"80%",
		buttons:buttons,
	});
}

function setProductDialogPlugin(element, table, model, id){
	var buttons = [{
		text: translate("label.button.save"),
		id:"save",
		submit:function(formContainer, params, $dialogbox){
			var idColumnValue = JSON.parse(params.json)[model.idColumn];
			$.post("/product/json/admin/Save.spr", params, function(answer){
				processRequestResult(formContainer, answer, $('.dialogbox-message'));
				if (answer.result > 0){
					updateTable(table, model, idColumnValue, answer.data);
					$dialogbox.remove();
				}
			});
		}
	},
	{
		text: translate("label.button.delete"),
		id:"delete",
		submit:function(formContainer, params, $dialogbox){
			var idColumnValue = JSON.parse(params.json)[model.idColumn];
			$.post("/product/json/admin/Delete.spr", params, function(answer){
				processRequestResult(formContainer, answer, $('.dialogbox-message'));
				if (answer.result > 0){
					updateTable(table, model, idColumnValue, answer.data);
					$dialogbox.remove();
				}
			});
		}
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


function sendDealFormData(formContainer, url){
	return sendFormData(formContainer, function (formContainer, params){
				$.post(url, params, function(answer){
					processRequestResult(formContainer, answer, $('.form-message'));
					if (answer.result > 0){
						$('#approve-button').hide();
						$('#reject-button').hide();
					}
				});
			}); 
}

function initDealForm(formContainer, url, id){
	setFormData(formContainer, url, {id:id}, function(deal){
		if (deal.status != 'ON_APPROVE'){
			$("#approve").hide();
			$('#reject').hide();
		}
		$.getScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initDealMap");
	})
}