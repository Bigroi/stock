'use strict';
var l10n = { 
	translate: function(word){
		if (typeof word !== 'object'){
			word = [word];
		}
		var result = "";
		for (var i = 0; i < word.length; i++){
			var translation = this._l10n[word[i].trim()];
			if (translation || translation == ''){
				result += translation + " ";
			} else {
				result += "not found " + word[i] + " ";
			}
		}
		return result;
	},
	_l10n:{},
	localization: function(){
		var l10n = this._l10n;
	    $.ajax(getContextRoot() + "/l10n/json/Labels.spr",{
	    	type: "GET",
	    	success: function(answer){
	    				try{
	    					for (var key in answer.data){
	    						l10n[key] = answer.data[key];
	    					}
	    				}catch(e){
	    					console.log(e);
	    				}
	    			},
	    	dataType: "json",
	    	async: false
	    })
	}
};

l10n.localization();

$(document).ready(function(){
	$(".language-switcher").change(function(){
		$.post(getContextRoot() + "/l10n/json/ChangeLanguage.spr",{lang:$(".language-switcher").val()}, function(answer){
			if (answer.result == 1){
				location.reload();
			}
		});
	});
});