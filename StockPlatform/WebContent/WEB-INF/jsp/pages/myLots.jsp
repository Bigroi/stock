<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<h1>${lable.myLots.title}</h1>
	<div id = "tableContainer">	
		<script>
			$(function(){
				$("#tableContainer").tableMaker("/lot/json/MyList.spr", "/lot/Form.spr?id={id}");
			});
		</script>
	</div>
	<form action="/lot/Form.spr">
		<input type="submit" value="${lable.button.create}">
	</form>
