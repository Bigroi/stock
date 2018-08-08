$(document).ready(function(){
	$('.test-trade').on("click", function(evt){
		$table = $("#main-table-deal");
		$table.DataTable().destroy();
		makeTable("/deal/json/TestTrade.spr", $table);
		return false;
	});
});