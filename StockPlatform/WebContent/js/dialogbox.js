"use strict";
$.fn.dialogbox = function(params) {
	var params = $.extend({
		"hasCloseButton"  : true,
		"hasCloseOverlay" : true,
		"prvDft"		  : true,
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
				var $button = $("<button>");
				$button.addClass("submit "+i+"button");
				$button.text(params.buttons[i].text);	    	
				$button.attr("id", params.buttons[i].id);
				
				var url = getUrl(params.buttons[i]);
				var close = params.buttons[i].close;
				if (url && close){
					$button.attr("url",url);
					$button.on("click",applayFormAndClose);
				} else if (url){
					
					$button.attr("url",url);
					$button.on("click",applayForm);
				} else {
					$button.on("click",deleting);	    		
				}
				$listelement.append($button);
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
			$button.css("background",params.btnBackground);
			
			if(params.prvDft){
				$(this).on("click",function(e) {
					e.preventDefault();
				});
			
			}
			
			loadData();
			
			function getUrl(button){
				if (!button.url){
					return;
				} else if (!button.formParams){
					return button.url;
				} else {
					var paramsStr = "";
					for (var name in button.formParams){
						if (button.formParams[name]){
							if (paramsStr != ""){
								paramsStr += "&";
							}
							paramsStr += encodeURIComponent(name) + "=" 
								+ encodeURIComponent(button.formParams[name]);
						}
					}
					return button.url + "?" + paramsStr
				}
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
			
			function applayForm(event) {
				event.preventDefault();
				sendFormData($dialogboxElementContent, $(this).attr("url"), function(answer){
					var $message = $(".dialogbox-message");
					if (answer.result > 0){
						$message.css("background-color", "green");
					} else if (answer.result < 0){
						$message.css("background-color", "red");
					} else {
						document.location = answer.data;
					}
					$message.html(answer.data);
				});
			};
			
			function applayFormAndClose(event){
				event.preventDefault();
				sendFormData($dialogboxElementContent, $(this).attr("url"), function(answer){
					var $message = $(".dialogbox-message");
					if (answer.result > 0){
						deleting();
						return;
					} else if (answer.result < 0){
						$message.css("background-color", "red");
					} else {
						document.location = answer.data;
					}
					$message.html(answer.data);
				});
			}
		});	 
	});		
}



 