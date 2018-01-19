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
	
	function _createTableHead(tHead, data){
		var headers = data.headers;
		var model = data.model;
		
		var tRow = $("<tr>");	
		if ($("#button-container").length > 0 && model.buttons){
			var th = $("<th>");
			tRow.append(th);
		}
		for(var i = 0; i < headers.length; i++){
			if(i == model.idColumn){
				continue;
			} 
			var th = $("<th>");
			var label = l10n[headers[i]];
			if(label){
				th.text(label);
			}else{
				th.text(headers[i]);
			}
			tRow.append(th);
		}
		tHead.append(tRow);	
	}
	
	function _createTableRow(tBody, row, model){
		var tRow = $("<tr>");
		var idValue = row[model.idColumn];
		_addEventListenerOnRow(tRow, idValue);
		if ($("#button-container").length > 0 && model.buttons){
			_addCheckbox(tRow, idValue)
		}
		for(var k = 0;  k < row.length; k++){
			if(k == model.idColumn){
				continue;
			}
			var td = $("<td>");
            var tdContent = row[k];
			var floatColumns = model.floatColumns;
			if(floatColumns.indexOf(k) >= 0){
				tdContent = tdContent*1;
				tdContent = tdContent.toFixed(2);
			}
			td.text(tdContent);
			tRow.append(td);
		}
		tBody.append(tRow);
	}
	
	function _addCheckbox(tRow, id){
		var td = $("<td><input type='checkbox' value='" + id + "' name='id'></td>");
		td.on("click", function(e){
			var chk = $(this).closest("tr").find("input:checkbox").get(0);
		    if(e.target != chk){
		        chk.checked = !chk.checked;
		    }
		    event.stopPropagation();
		});
		tRow.append(td);
	}
	
	function _addEventListenerOnRow(tRow, id){
		if (typeof(formPage) === "string"){
			var formUrl = formPage.replace("{id}", id); 
			tRow.attr("href", formUrl);
		} else {
			formPage(tRow, id);
		}
	}
	
	function _createTableBody(tBody, data){
		for(var j = 0; j < data.rows.length; j++){
			_createTableRow(tBody, data.rows[j], data.model);
		}
	}
	
	function _addEventListener(tBody){
		tBody.on("click", function (e){
			if (e.target.parentNode.getAttribute("href")){
				document.location.href = e.target.parentNode.getAttribute("href");
			}
		});	
	}
	
	function _addButtons(buttons){
		for (var i = 0; i < buttons.length; i++){
			var button = $("<button class='submit' url='" + buttons[i].url + "'>"
								+ buttons[i].name + 
							"</button>");
			button.on("click", function(e){
				return sendFormData($("div[id$='form-container']"), $(e.target).attr("url"), function(answer){
					if (answer.result < 0){
						alert(answer.data);
					} else {
						document.location = answer.data;
					}
				});
			});
			$("#button-container").append(button);
		}
	}
	
	function createTable (dataObj) {
		var buttonContainer = $("#button-container");
		var table = $("<table class='baseTable'>");
		var tHead = $("<thead>");
		var tBody = $("<tbody>");
		var ceckboxColumn = $("#button-container").length > 0 && dataObj.data.model.buttons;
		
		_createTableHead(tHead, dataObj.data);
		_createTableBody(tBody, dataObj.data)
		_addEventListener(tBody)
		
		table.append(tHead);
		table.append(tBody);
		
		if ($("#button-container").length > 0 && dataObj.data.model.buttons){
			_addButtons(dataObj.data.model.buttons);
		}
		
		return table;
	}
}