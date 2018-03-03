'use strict';
window.l10n = { };
$.fn.localization = function(){
  	var labels = this[0].textContent.split("\n");
    $.ajax("/l10n/json/Labels.spr",{
    	type: "POST",
    	data: {json: JSON.stringify(labels)}, 
    	success: function(answer){
    				try{
    					window.l10n = answer.data;
    				}catch(e){
    					console.log(e);
    				}
    			},
    	dataType: "json",
    	async: false
    })
}

function translate(word){
	var translation = window.l10n[word];
	if (translation || translation == ''){
		return translation;
	} else {
		return "not found " + word;
	}
}