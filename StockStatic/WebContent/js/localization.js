'use strict';
var l10n = { 
	translate: function(word){
		var translation = this._l10n[word];
		if (translation || translation == ''){
			return translation;
		} else {
			return "not found " + word;
		}
	},
	_l10n:{},
	localization: function(){
		var l10n = this._l10n;
		var contextRoot = $("meta[name=context-root]").attr("content");
		contextRoot = contextRoot ? contextRoot : "";
		console.log(contextRoot);
	    $.ajax(contextRoot+"/l10n/json/Labels.spr",{
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