'use strict';
function makeTable(url, table){
	$.getJSON(url, function(answer){
		var model = answer.model;
		var tableData = answer.table;
		tableData.rowCallback = function(row, data, index) {
			var statusTd = row.children[model.statusColumn];
			var fieldName = tableData.columns[model.statusColumn].data;
			if (data[fieldName] == "ACTIVE"){
				var switcher = document.createElement("div");
				switcher.classList.add("swtitch-row-on");
				statusTd.textContent = "";
				statusTd.appendChild(switcher);
			}else{
				var switcher = $("<div class='swtitch-row-off'>");
				statusTd.textContent = "";
				statusTd.appendChild(switcher[0]);
			}
			if(model.editRemove){
				var editRemove = row.children[model.editRemove];
				var edit = document.createElement("div");
				edit.classList.add("edit");
				var remove = document.createElement("div");
				remove.classList.add("remove");
				editRemove.textContent = "";
				editRemove.appendChild(edit);
				editRemove.appendChild(remove);
			}
		}
		$(table).DataTable(tableData);
	});
}