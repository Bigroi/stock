'use strict'; 
$.fn.tableMaker = function (srcTable, formPage) {
		
	var divToAdd = $(this);
	var tableDiv = this;
    	
	$.getJSON(srcTable,  function (answer){ 
		if(answer.result !== 1){
			alert(answer.data);
			return;
		}
		for(var key in answer.data.model.custSortFn){
			eval(answer.data.model.custSortFn[key]);
		}
		divToAdd.append(createTable (answer));
		tableDiv.find("table").tableSorter(answer.data.model); 
	});	
	
	function createTable (dataObj) {
		var table = $("<table>");
		table.addClass("baseTable");
		var tHead = $("<thead>");
		var tBody = $("<tbody>");
		
		var tRow = $("<tr>");	
		for(var i = 0; i < dataObj.data.headers.length; i++){
			if(i == dataObj.data.model.idColumn){
				continue;
			} 
			var th = $("<th>");
			var lable = l10n[dataObj.data.headers[i]];
			if(lable){
				th.text(lable);
			}else{
			th.text(dataObj.data.headers[i]);
			}
			tRow.append(th);
		}
		tHead.append(tRow);	
		
		for(var j = 0; j < dataObj.data.rows.length; j++){
			tRow = $("<tr>");
			var idValue = dataObj.data.rows[j][dataObj.data.model.idColumn];
			if (typeof(formPage) === "string"){
				var formUrl = formPage.replace("{id}", idValue); 
				tRow.attr("href", formUrl);
			} else {
				formPage(tRow, idValue);
			}
			for(var k = 0;  k < dataObj.data.rows[j].length; k++){
				if(k == dataObj.data.model.idColumn){
					continue;
				}
				var td = $("<td>");
				td.text(dataObj.data.rows[j][k]);
				tRow.append(td);
			}
			tBody.append(tRow);			
		}
		
		tBody.on("click", function (e){
			if (e.target.parentNode.getAttribute("href")){
				document.location.href = e.target.parentNode.getAttribute("href");
			}
		});	
		
		table.append(tHead);
		table.append(tBody);
		return table;
	}
}