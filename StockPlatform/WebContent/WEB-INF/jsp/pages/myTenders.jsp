
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="tender-form-container"></div>
<div id="table-form-container">
	<h1>${label.myTenders.title}</h1>
	<form action="#" class="form">
		<ul>
			<li>
				<button class="submit" id="new-lot-button" >${label.button.create }</button>
				<script type="text/javascript">setTenderDialogPlugin($("#new-lot-button"))</script>
			</li>
		
			<li>
				<div id = "tableContainer">	
					<script>
						$(function(){
							$("#tableContainer").tableMaker("/tender/json/MyList.spr", setTenderDialogPlugin);
						});
					</script>
				</div>
			</li>
			<li id="button-container"></li>
		</ul>
	</form>
</div>
