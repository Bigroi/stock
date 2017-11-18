<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>User List</h1>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/user/json/admin/List.spr", "/json/product/form.spr?id={id}");
		});
	</script>
</div>
<form action="/admin/Index.spr">
	<input type="submit" value="Welcome page">
</form>
