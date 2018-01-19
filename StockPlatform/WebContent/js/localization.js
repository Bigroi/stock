'use strict';
window.l10n = { };
$.fn.localization = function(){
  	var lables = this[0].textContent.split("\n");
	console.log({json: lables});
    $.ajax("/l10n/json/Lables.spr",{
    	type: "POST",
    	data: {json: JSON.stringify(lables)}, 
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