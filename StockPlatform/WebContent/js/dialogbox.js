"use strict";
$.fn.dialogbox = function(params) {
	var params = $.extend({
		'element'         : $('<div>Insert any Element</div>'),
		'buttons' 		  : ["exempleBtn","exempleBtn1"],
		"hasCloseButton"  : true,
		"hasCloseOverlay" : true,
		"prvDft"		  : true,
		
		
	},params);

	return this.on("click",function() {	
		$("#form-container").load("file:///D:/Stock/оы/dialogbox.html",function() {

			var $dialogbox = $(".dialogbox");
			var $dialogboxChild = $(".dialogbox-child");
			var $dialogboxElementContent = $(".dialogbox-elementContent");
			var $dialogboxButtons = $(".dialogbox-Buttons");
			var $dialogboxHead = $(".dialogbox-Head"); 				
			$dialogboxChild.css("width",params.width);
			$dialogboxChild.css("height",params.height);	
			var $spanClose = $("<span class='dialogbox-spanClose'>&times</span>");

			if(typeof params.element == "string"){
				$(".dialogbox-elementContent").load(params.element);
			}else{
				$(params.element)
				.clone()
				.appendTo($dialogboxElementContent)
			
			}
				
			for(var i = 0; i < params.buttons.length; i++){
				var $button = $("<button>");
				$button.addClass("dialogbox-btn "+i+"button");
				$button.text(params.buttons[i].text);	    	
				
				
				var url = params.buttons[i].url;
				var close = params.buttons[i].close;
				if (url && close){
					$button.attr("url",url);
					$button.on("click",sendFormDataAndClose);
				} else if (url){
					$button.attr("url",url);
					$button.on("click",sendFormData);
				} else {
					$button.on("click",deleting);	    		
				}
				$dialogboxButtons.append($button);
			}	 

			if(params.hasCloseButton){	    	
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
			$spanClose.css("color",params.spanCloseColor);

			if(params.prvDft){
				$(this).on("click",function(e) {
					e.preventDefault();
				});
			
			}
			function deleting() {
				$dialogbox.remove();
			}
			
			function getFormData(event, button){
				var $form = button.parent().parent().find("[name]");
				var $form_Length = $form.children("[name]").length;
				event.preventDefault();
				var  data = {};              
				for(var i = 0; i < $form_Length;i++){
					var name = $form[i].getAttribute("name");
					var value = $form[i].value;
					data[name]=value;                   
				}
				data = JSON.stringify(data,"",1);
				return data;
			}
			
			function sendFormData(event) {
				var button = $(this);
				var data = getFormData(event, button);
				$.post(button.attr("url"),{json:data},function(answer){
					var $message = $(".dialogbox-message");
					if (answer.result > 0){
						$message.css("background-color", "green");
					} else {
						$message.css("background-color", "red");
					}
					$message.html(answer.data);
				}, "json");

			};
			
			function sendFormDataAndClose(event){
				var button = $(this);
				var data = getFormData(event, button);
				$.post(button.attr("url"),{json:data},function(answer){
					if (answer.result > 0){
						deleting();
					} else {
						var $message = $(".dialogbox-message");
						$message.css("background-color", "red");
						$message.html(answer.data);
					}
				}, "json");
			}
		})	 

				
	});		
}



 