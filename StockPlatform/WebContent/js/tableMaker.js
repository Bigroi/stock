'use strict'; 
$.fn.tableMaker = function () {
		
	var divToAdd = this;
    var srcTable = this[0].getAttribute("data-url");
	
	
	$.getJSON(srcTable,  function (answer){ 
/* !!!Обязательно использовать имя параметра функции answer, т.к. оно используется в json'e */	
		try{
			for(var key in answer.data.model.custSortFn){
				if(typeof answer.data.model.custSortFn[key] === "string"){ //нужна ли проверка???
					eval(answer.data.model.custSortFn[key]);
				}
			}
			divToAdd[0].append(createTable (answer.data.headers, answer.data.rows));
			divToAdd.find("table").tableSorter(answer.data.model); 
			// кривовато, но find работает только на коллекции
		}catch(e){
			console.log(e);
		}
	});	
	
	/*
	var answer = {
		"result": 1,
		"data": {
			"model":{
				"custSortFn":{
					"1": function (a, b){
						return (a - b);
					},
					"2": function (a, b){
						return (a - b);
					}
				},
				"allowSorting":{	
					"3": false
				}
			},
			"headers":["Наименование", "Количество, т", "Цена за тонну, руб", "Место загрузки"],
			"rows":[
				["Картофель", "10.5", "700", "Барановичи"],
				["Яблоки", "5.35", "1200", "Логойск"],
				["Хурма", "3.5", "5000", "Минск"],
				["Ананас", "50.0", "100", "Витебск"]
			]
		}
	}	
	*/
	function createTable (arrHeaders, arrRows) {	//добавить обработку answer.result
		var table = document.createElement("table");
		var tHead = document.createElement("thead");
		var tBody = document.createElement("tbody");
		var tRow = [];
		tRow[0] = document.createElement("tr");	
		for(var i = 0; i < arrHeaders.length; i++){
			var th = document.createElement("th");
			th.textContent = arrHeaders[i];
			tRow[0].appendChild(th);
		}
		tHead.appendChild(tRow[0]);	
		for(var j = 0; j < arrRows.length; j++){
			tRow[j+1] = document.createElement("tr");
				for(var k = 0;  k <arrRows[j].length; k++){
					var td = document.createElement("td");
					td.textContent = arrRows[j][k];
					tRow[j+1].appendChild(td);
				}
			tBody.appendChild(tRow[j+1]);			
		}
				
		table.appendChild(tHead);
		table.appendChild(tBody);
		return table;
	}
	
	//this[0].append(createTable (answer.data.headers, answer.data.rows));	
	//this.find("table").tableSorter(answer.data.model); //почему не работает this[0]?
	
}