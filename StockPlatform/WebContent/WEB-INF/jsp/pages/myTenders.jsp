
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<h1>${lable.myTenders.title}</h1>
	<div id = "tableContainer">	
		<script>
			$(function(){
				$("#tableContainer").tableMaker("/tender/json/MyList.spr", "/tender/Form.spr?id={id}");
			});
		</script>
	</div>
	<form action="/tender/Form.spr">
		<input type="submit" value="${lable.button.create }">
	</form>