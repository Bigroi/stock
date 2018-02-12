"use strict";
$.fn.dialogbox = function(params) {
	var params = $.extend({
		"hasCloseButton"  : true,
		"hasCloseOverlay" : true,
	},params);

	return this.on("click",function() {	
		params.container.load(params.formUrl, function() {

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
			
			var $listelement = $("<li>");
			$listelement.css("text-align", "center");
			for(var i = 0; i < params.buttons.length; i++){
				addButton($listelement, params.buttons[i]);
			}	
			$formList.append($listelement);

			if(params.hasCloseButton){	
				var $spanClose = $("<span class='dialogbox-spanClose'>&times</span>");
				$spanClose.css("color",params.spanCloseColor);
				$dialogboxHead.append($spanClose);
			}

			$dialogbox.on("click",function (e) {        			 			 											
				if(params.hasCloseOverlay && e.target.className == "dialogbox"){		  
					$dialogbox.remove();
				}
				if(e.target.className=="dialogbox-spanClose"){	
					$dialogbox.remove();
				}   			
			})      
			
			loadData();
			
			function addButton($listelement, buttonDef){
				var $button = $("<button>");
				$button.addClass("submit "+i+"button");
				$button.text(buttonDef.text);	    	
				$button.attr("id", buttonDef.id);
				$button.attr("type", "submit");
				
				$button.click(function(event){
					return sendFormData(
							$(event.target).parent().parent().parent(),  
							buttonDef.submit, 
							buttonDef.login);
				});
				
				$listelement.append($button);
			}
			
			function deleting() {
				$dialogbox.remove();
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
	});		
}



 