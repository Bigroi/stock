
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="tender-form-container"></div>
<h1>${lable.myTenders.title}</h1>
<div id = "lables">lable.tender.product,lable.tender.status,lable.tender.max_price,lable.tender.max_volume,lable.tender.exp_date,lable.tender.creationDate</div>
<script>
$("#lables").localization();
</script>
<form action="#" class="form">
	<ul>
		<li>
			<button class="submit" id="new-lot-button" >${lable.button.create }</button>
			<script type="text/javascript">setTenderDialogPlugin($("#new-lot-button"))</script>
		</li>
	</ul>
</form>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/tender/json/MyList.spr", setTenderDialogPlugin);
		});
	</script>
</div>
