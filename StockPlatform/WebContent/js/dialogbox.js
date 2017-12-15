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
			var $dialogboxButtons = $(".dialogbox-Buttons");
			var $dialogboxHead = $(".dialogbox-Head"); 				
			$dialogboxChild.css("width",params.width);
			$dialogboxChild.css("min-height",params.height);	
			
				
			for(var i = 0; i < params.buttons.length; i++){
				var $button = $("<button>");
				$button.addClass("dialogbox-btn "+i+"button");
				$button.text(params.buttons[i].text);	    	
				
				
				var url = params.buttons[i].url;
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
				$dialogboxButtons.append($button);
			}	 

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
			function deleting() {
				$dialogbox.remove();
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



 