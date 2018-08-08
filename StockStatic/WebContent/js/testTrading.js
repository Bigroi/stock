$(document).ready(function(){
	$('.test-trade').on("click", function(evt){
		$.getJSON(getContextRoot() + "/deal/json/TestTrade.spr", function(answer){
			if (answer.result > 0){
				var dataTable = $("#main-table-deal").DataTable();
				dataTable.clear();
				dataTable.rows.add(answer.data.table.data).draw(true);
			}
		});
	});
	
	$('.test-clear').on("click", function(evt){
		$.getJSON(getContextRoot() + "/deal/json/Clean.spr", function(answer){
			if (answer.result > 0){
				$("#main-table-deal").DataTable().clean();
				$("#main-table-lot").DataTable().clean();
				$("#main-table-tender").DataTable().clean();
			}
		});
	});
});