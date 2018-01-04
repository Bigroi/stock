'use strict';
var l10n = {};
$.fn.localization = function(){
  	var lables = this[0].textContent.split("\n");
	console.log({json: lables});
    $.post("/l10n/json/Lables.spr",{json: JSON.stringify(lables)}, function(answer){
        try{
            l10n = answer.data;
		}catch(e){
			console.log(e);
		}
    }, "json");
}