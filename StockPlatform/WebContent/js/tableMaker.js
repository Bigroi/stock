'use strict'; 
$.fn.tableMaker = function (srcTable, srcForm) {
		
	var divToAdd = this;
    	
	$.getJSON(srcTable,  function (answer){ 
	/* !!!Обязательно использовать имя параметра функции answer, т.к. оно используется в json'e 
	в поле customSortFn (строки 6  и 7).*/	
		if(answer.result !== 1){
			alert(answer.data);
			return;
		}
		try{
			for(var key in answer.data.model.custSortFn){
				eval(answer.data.model.custSortFn[key]);
			}
			divToAdd[0].append(createTable (answer));
			divToAdd.find("table").tableSorter(answer.data.model); 
			//кривовато, но find работает только на коллекции
		}catch(e){
			console.log(e);
		}
	});	
	
	function createTable (dataObj) {
		var table = document.createElement("table");
		table.classList.add("baseTable");
		var tHead = document.createElement("thead");
		var tBody = document.createElement("tbody");
		var tRow = [];
		
		tRow[0] = document.createElement("tr");	
		for(var i = 0; i < dataObj.data.headers.length; i++){
			if(i == dataObj.data.model.idColumn){
				continue;
			} 
			var th = document.createElement("th");
			th.textContent = dataObj.data.headers[i];
			tRow[0].appendChild(th);
		}
		tHead.appendChild(tRow[0]);	
		
		for(var j = 0; j < dataObj.data.rows.length; j++){
			tRow[j+1] = document.createElement("tr");
			var formUrl = srcForm.replace("{id}", dataObj.data.rows[j][dataObj.data.model.idColumn]); 
			tRow[j+1].setAttribute("href", formUrl);//??????attrName
				for(var k = 0;  k < dataObj.data.rows[j].length; k++){
					if(k == dataObj.data.model.idColumn){
						continue;
					}
					var td = document.createElement("td");
					td.textContent = dataObj.data.rows[j][k];
					tRow[j+1].appendChild(td);
				}
			tBody.appendChild(tRow[j+1]);			
		}
		
		tBody.addEventListener("click", function (e){
			document.location.href = e.target.parentNode.getAttribute("href");
		});	
		
		table.appendChild(tHead);
		table.appendChild(tBody);
		return table;
	}
}