'use strict';
var l10n;
$.fn.localization = function(){
  	var lables = this[0].textContent.split(",");
	console.log({json: lables});
    $.post("localization.json",{json: lables}, function(answer){
        try{
            l10n = answer;
		}catch(e){
			console.log(e);
		}
    }, "json");
}