'use strict';
window.l10n = { };
function localization(){	
    $.ajax("/l10n/json/Labels.spr",{
    	type: "GET",
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