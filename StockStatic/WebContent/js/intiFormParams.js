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


function getPasswordResetDialogParams(){
	return {
		formUrl:"/ResetForm", 
		dialogClass: "password-reset-dialogbox", 
		buttons:[
		{
			text: l10n.translate("label.button.reset"),
			id: "password-reset",
			submitUrl : "/account/json/ResetPassword"
		}]			
	};
}

function getLoginDialogParams(){
	return {
		formUrl:"/Login",
		dialogClass: "login-dialogbox", 
		buttons:[
		{
			text: l10n.translate("label.button.login"),
			id:"login",
			submitUrl: "/account/json/Login",
			login:true
		}], 				
	};
}

function getAddressDialogParams(id, $table, model){
	return {
		formUrl:"/address/Form", 
		formParams:{id:id},
		formData:"/address/json/Form",
		dialogClass: "address-dialogbox", 
		buttons:[
		{
			text: l10n.translate("label.button.save"),
			id: "save",
			submitUrl: "/address/json/Save",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		}]
	};
}

function getReginDialogParams(){
	return {
		formUrl:"/Registration", 
		dialogClass: "registration-dialogbox", 
		buttons:[
		{
			text: l10n.translate("label.button.finishRegistration"),
			id: "finish-registration",
			submitUrl : "/account/json/Registration"
		}]			
	};
}

function getLotDialogParams(id, $table, model){
	return {
		formUrl: "/lot/Form", 
		formParams:{id:id},
		formData: "/lot/json/Form",
		dialogClass: "lot-dialogbox", 
		buttons: [{
				text: l10n.translate("label.button.save"),
				id:"save",
				buttonClass:"gray-button",
				submitUrl: "/lot/json/Save",
				submitFunction: buttonCallbackWithTableUpdate,
				table : $table, 
				model : model 
			},
			{
				text: l10n.translate("label.button.save_start_trading"),
				id:"save-start-trading",
				submitUrl: "/lot/json/SaveAndActivate",
				submitFunction: buttonCallbackWithTableUpdate,
				table : $table, 
				model : model
			}]
	};	
}

function getTestLotDialogParams(id, $table, model){
	return {
		formUrl: "/lot/TestForm", 
		dialogClass: "lot-dialogbox", 
		buttons: [
			{
				text: l10n.translate("label.button.save_start_trading"),
				id:"save-start-trading",
				submitUrl: "/lot/json/TestSave",
				submitFunction: buttonCallbackWithTableUpdate,
				table : $table, 
				model : model
			}]
	};	
}

function getAccountDialogParams(id){
	return {
		formUrl: "/account/Form", 
		formParams:{id:id},
		formData: "/account/json/Form",
		dialogClass: "account-dialogbox",
		buttons: [{
				text: l10n.translate("label.button.save"),
				id:"save",
				submitUrl: "/account/json/Save"
			}],
		afterLoad:selectLanguages
	};
	
	function selectLanguages(){
	    $.widget( "custom.iconselectmenu", $.ui.selectmenu, {
		      _renderItem: function( ul, item ) {
		        var li = $( "<li>" ),
		          wrapper = $( "<div>", { text: item.label } );
		        if ( item.disabled ) {li.addClass( "ui-state-disabled" );}
		        $( "<span>", {style: item.element.attr( "data-style" ),"class": "ui-icon " + item.element.attr( "data-class" )
		        }).appendTo( wrapper );
		        return li.append( wrapper ).appendTo( ul );
		      }
		    });
		    $("#languages-select").iconselectmenu().iconselectmenu("menuWidget").addClass("ui-menu-icons customicons");
		    var selectedImage = $("#languages-select").find("option:selected").attr("data-class");
		    $(".ui-selectmenu-text").css('background-image', 'url("/Static/img/' + selectedImage +'.png")');
		    $( "#languages-select" ).iconselectmenu({
		    	select: function(event, ui){
			        var selectedImage = $(this).find("option:selected").attr("data-class");
			        $(".ui-selectmenu-text").css('background-image', 'url("/Static/img/' + selectedImage +'.png")');
		    	}
		    });
	}
}

function getContactUsDialogParams(){
	return {
		container:$("#message-dialog-container"),
		formUrl: "/feedback/Form", 
		formData: "/feedback/json/Form", 
		dialogClass: "contact-us-dialogbox",
		buttons:[{
			text: l10n.translate("label.button.send"),
			id:"contact-us",
			submitUrl: "/feedback/json/Save"
		}]
	};
}

function getMessageDialogParams(message, type, action){
	return {
		container:$("#message-dialog-container"),
		formUrl:"/Message", 
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
		formUrl: "/tender/Form", 
		formParams:{id:id},
		formData: "/tender/json/Form",
		dialogClass: "tender-dialogbox",
		buttons:[{
			text: l10n.translate("label.button.save"),
			id:"save",
			buttonClass:"gray-button",
			submitUrl: "/tender/json/Save",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		},
		{
			text: l10n.translate("label.button.save_start_trading"),
			id:"save-start-trading",
			submitUrl: "/tender/json/SaveAndActivate",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		}],
	};
}

function getTestTenderDialogParams(id, $table, model){
	return{
		formUrl: "/tender/TestForm", 
		dialogClass: "tender-dialogbox",
		buttons:[
		{
			text: l10n.translate("label.button.save_start_trading"),
			id:"save-start-trading",
			submitUrl: "/tender/json/TestSave",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		}],
	};
}

function getProductDialogParams(id, $table, model){
	return {
		formUrl: "/product/admin/Form", 
		formParams:{id:id},
		formData: "/product/json/admin/Form",
		dialogClass: "product-dialogbox",
		buttons:[{
			text: l10n.translate("label.button.save"),
			id:"save",
			submitUrl: "/product/json/admin/Save",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		},
		{
			text: l10n.translate("label.button.delete"),
			id:"delete",
			submitUrl: "/product/json/admin/Delete",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		},
		{
			text: l10n.translate("label.button.edit_categories"),
			id:"delete",
			submitUrl: "/product/json/admin/Categories"
		}]
	};
}

function getProductCategoryDialogParams(id, $table, model){
	return {
		formUrl: "/category/Form", 
		formParams:{id:id, productId:$("input[name=productId]").val()},
		formData: "/category/json/Get",
		dialogClass: "product-dialogbox",
		buttons:[{
			text: l10n.translate("label.button.save"),
			id:"save",
			submitUrl: "/category/json/Save",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		},
		{
			text: l10n.translate("label.button.delete"),
			id:"delete",
			submitUrl: "/category/json/Delete",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		}]
	};
}

function getLabelDialogParams(id, $table, model){
	return {
		formUrl: "/label/admin/Form", 
		formParams:{id:id},
		formData: "/label/json/admin/Form",
		dialogClass: "label-dialogbox",
		buttons:[{
			text: l10n.translate("label.button.save"),
			id:"save",
			submitUrl: "/label/json/admin/Save",
			submitFunction: buttonCallbackWithTableUpdate,
			table : $table, 
			model : model
		}]
	};
}

function getDealFeedbackDialogParams(dealId){
	return {
		formUrl: "/deal/feedback/Form", 
		formParams:{dealId:dealId},
		formData: "/deal/feedback/json/Form",
		dialogClass: "dealFeedback-dialogbox",
		buttons: [{
				text: l10n.translate("label.button.save"),
				id:"save",
				submitUrl: "/deal/feedback/json/Save"
			}]
	};	
}
 function getCommentsDialogParams(){
	return {
		formUrl:"/deal/Comments", 
		formData:"/deal/json/Comments",
		//dialogClass: "comment-dialogbox"
		
	};
}
