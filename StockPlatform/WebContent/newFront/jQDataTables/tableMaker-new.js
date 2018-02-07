'use strict';
function makeTable(url, table){
	$.getJSON(url, function(answer){
		var model = answer.model;
		var tableData = answer.table;
		tableData.rowCallback = function(row, data, index) {
			var statusTd = row.children[model.statusColumn];
			var fieldName = tableData.columns[model.statusColumn].data;
            var $switcher = $("<div class='swtitch-row-off'>");
			if (data[fieldName] == "ACTIVE"){
				$switcher.removeClass("swtitch-row-off");
				$switcher.addClass("swtitch-row-on");
			}
            statusTd.textContent = "";
            $switcher.click(function (event){
                event.target.classList.toggle("swtitch-row-off");
                event.target.classList.toggle("swtitch-row-on");
				data[fieldName] = data[fieldName] == "ACTIVE" ? "INACTIVE" :"ACTIVE";
            });
            statusTd.appendChild($switcher[0]);
            
			if(model.editRemove){
				var editRemove = row.children[model.editRemove];//получаем ячейку
                editRemove.textContent = "";//убираем текст
                
                var $edit = $("<div class='no-edit'>");//создаём элемент для символа EDIT
                //по умолчанию делаем фон элемента цвета строки 
                var $remove = $("<div class='no-remove'>");//создаём элемент для символа REMOVE
                //по умолчанию делаем фон элемента цвета строки 
                
                var fieldNameEdit = tableData.columns[model.editRemove].data;//получаем имя поля в объекте строки
                if(data[fieldNameEdit][0] == "Y"){ 
                    $edit.removeClass("no-edit");
                    $edit.addClass("edit");
                }
				if(data[fieldNameEdit][1] == "Y"){ 
                    $remove.removeClass("no-remove");
                    $remove.addClass("remove");
                }
				editRemove.appendChild($edit[0]);
				editRemove.appendChild($remove[0]);
			}
		}
		$(table).DataTable(tableData);
	});
}