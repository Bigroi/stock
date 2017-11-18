
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<h1>My tenders</h1>
	<div id = "tableContainer">	
		<script>
			$(function(){
				$("#tableContainer").tableMaker("/tender/json/MyList.spr", "/tender/Form.spr?id={id}");
			});
		</script>
	</div>
	<form action="/tender/Form.spr">
		<input type="submit" value="Add tender">
	</form>
	<form action="/Index.spr">
		<input type="submit" value="Welcome page">
	</form>
