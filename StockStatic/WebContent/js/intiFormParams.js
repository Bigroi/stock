/*
 * {
		formUrl: "string",                  // url to get form html 
		formParams: {},                     // params to get form data
		formData: "string",                 // url to get form data
		dialogClass: "string",              // name of the class of dialogbox root element
		login: bool                         // true for login only
		buttons:[{
			text: l10n.translate("string"), // display name of the button
			id:"string",                    // id of the buuton
			buttonClass:"string"            // class for the button
			submitUrl: "string",            // url to send form data
			submitFunction: function(){}    // click callback function. default is **simpleButtonCallback()**
			 								// for tables use **buttonCallbackWithTableUpdate()**
			table : $table,                 // table to update
			model : model                   // table model
		}],
	}
 * @returns
 */

function getLoginDialogParams(){
	return {
		formUrl:"/Login.spr",
		dialogClass: "login-dialogbox", 
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
		dialogClass: "address-dialogbox", 
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
		dialogClass: "registration-dialogbox", 
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
		dialogClass: "lot-dialogbox", 
		buttons: [{
				text: l10n.translate("label.button.save"),
				id:"save",
				buttonClass:"gray-button",
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
		dialogClass: "account-dialogbox",
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
		dialogClass: "contact-us-dialogbox",
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
		dialogClass: "message-dialogbox",
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
		dialogClass: "tender-dialogbox",
		buttons:[{
			text: l10n.translate("label.button.save"),
			id:"save",
			buttonClass:"gray-button",
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
		dialogClass: "product-dialogbox",
		buttons:[{
			text: l10n.translate("label.button.save"),
			id:"save",
			submitUrl: "/product/json/admin/Save.spr",
			submitFunction: buttonCallbackWithTableUpdate,
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