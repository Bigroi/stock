"use strict";
function showMessageDialog(message, type, action){
	showDialog(getMessageDialogParams(message, type, action));
}

function showDialog(params){
	params = addDefaults(params);
	params.container.load(params.formUrl, params.formParams, function() {
		var $dialogbox = $(".dialogbox");
		var $dialogboxElementContent = $(".dialogbox-elementContent");
		var $formList = $("#form-list");
		var $dialogboxHead = $(".dialogbox-Head");
		
		if (params.dialogClass){
			$dialogbox.addClass(params.dialogClass);
		}
		
		if (params.buttons){
			for(var i = 0; i < params.buttons.length; i++){
				addButton($formList, params.buttons[i]);
			}	
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
			if (buttonDef.buttonClass){
				$button.addClass(buttonDef.buttonClass);
			}
			
			$button.click(function(event){
				return sendFormData(
						$(event.target).parents('form:first'),  
						buttonDef,
						$dialogbox);
			});
			
			$listelement.append($button);
		}
		
		function loadData(){
			if (params.formData){
				$.post(params.formData, params.formParams, function(answer){
					setFormInputs($dialogboxElementContent, answer.data);
				}, "json");
			}
		}
		
	});	 
	return false;
	
	function addDefaults(params){
		params = Object.assign({}, params);
		if (!params.container){
			params.container = $("#form-container");
		}
		if (!params.hasCloseButton){
			params.hasCloseButton = true;
		}
		if (!params.hasCloseOverlay){
			params.hasCloseOverlay = true;
		}
		if (params.formUrl){
			params.formUrl = getContextRoot() + params.formUrl;
		}
		if (params.formData){
			params.formData = getContextRoot() + params.formData;
		} 
		var buttons = params.buttons;
		if (buttons){
			for (var i = 0; i < buttons.length; i++){
				var button = buttons[i];
				if (!button.submitFunction){
					button.submitFunction = simpleButtonCallback;
				}
				if (button.submitUrl){
					button.submitUrl = getContextRoot() + button.submitUrl;
				}
			}
		}
	}
}		
 