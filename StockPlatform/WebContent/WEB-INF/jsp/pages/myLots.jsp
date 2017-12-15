<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="lot-form-container"></div>
<h1>${lable.myLots.title}</h1>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/lot/json/MyList.spr", setLotDialogPlugin);
		});
	</script>
</div>
<form action="#" class="form">
	<ul>
		<li>
			<button class="submit" id="new-lot-button">${lable.button.create}</button>
			<script type="text/javascript">setLotDialogPlugin($("#new-lot-button"))</script>
		</li>
	</ul>
</form>
