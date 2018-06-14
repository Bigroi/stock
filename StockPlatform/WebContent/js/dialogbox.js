"use strict";
$.fn.dialogbox = function(params) {
	var params = $.extend({
		"hasCloseButton"  : true,
		"hasCloseOverlay" : true,
	},params);

	return this.on("click", function() {	
		return showDialog(params);
	});
}

function showMessageDialog(message, type, action){
	var params = {
		container:$("#message-dialog-container"),
		formUrl:"/Message.spr", 
		formParams:{message:message,type:type},
		height:"40%",
		"hasCloseButton":true,
		"hasCloseOverlay":true,
		buttons:[
			{
				text: l10n.translate("label.button.ok"),
				id:"ok",
				submit:function(formContainer, params, $dialogbox){
					$dialogbox.remove();
					if (action){
						action();
					}
				}
			}
		]
	};
	showDialog(params);
}

function showDialog(params){
	params.container.load(params.formUrl, params.formParams, function() {
		var $dialogbox = $(".dialogbox");
		var $dialogboxChild = $(".dialogbox-child");
		var $dialogboxElementContent = $(".dialogbox-elementContent");
		var $formList = $("#form-list");
		var $dialogboxHead = $(".dialogbox-Head");
		if (params.width){
			$dialogboxChild.css("width",params.width);
		}
		if (params.height){
			$dialogboxChild.css("height",params.height);
		}
		
		if (params.buttons){
			var $listelement = $("<li>");
			$listelement.css("text-align", "center");
			for(var i = 0; i < params.buttons.length; i++){
				addButton($listelement, params.buttons[i]);
			}	
			$formList.append($listelement);
		}
		if(params.hasCloseButton){	
			var $spanClose = $("<span class='dialogbox-spanClose'></span>");
			$spanClose.css("color",params.spanCloseColor);
			$dialogboxHead.append($spanClose);
		}

		$dialogbox.on("click",function (e) {  
			if(params.hasCloseOverlay && e.target.className == "dialogbox"){		  
				$dialogbox.remove();
			}
			if(e.target.className=="dialogbox-Head"){	
				$dialogbox.remove();
			}   			
		})      
		
		loadData();
		
		function addButton($listelement, buttonDef){
			var $button;
			if (buttonDef.type == "link"){
				$button = $("<a href='#'>");
			} else {
				$button	= $("<button>");
				$button.attr("type", "submit");
			}
			$button.addClass("submit button");
			$button.text(buttonDef.text);	    	
			$button.attr("id", buttonDef.id);
			
			
			$button.click(function(event){
				var result = sendFormData(
						$(event.target).parent().parent().parent(),  
						buttonDef.submit,
						$dialogbox,
						buttonDef.login);
				return result;
			});
			
			$listelement.append($button);
		}
		
		function loadData(){
			if (params.formData){
				setFormData($dialogboxElementContent, 
						params.formData, 
						params.formParams,
						params.afterLoad);
			}
		}
		
	});	 
	return false;
}		
 