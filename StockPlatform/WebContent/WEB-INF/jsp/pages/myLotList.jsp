<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<h1>My lots</h1>
	<div id = "tableContainer">	
		<script>
			$(function(){
				$("#tableContainer").tableMaker("/lot/json/MyList.spr", "/lot/Form.spr?id={id}");
			});
		</script>
	</div>
	<form action="/lot/Form.spr">
		<input type="hidden" name="id" value="-1" /> <input type="submit"
		value="Add lot">
	</form>
	<form action="/Index.spr">
		<input type="submit" value="Welcome page">
	</form>
