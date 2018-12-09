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
	    $.ajax(getContextRoot() + "/l10n/json/Labels",{
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