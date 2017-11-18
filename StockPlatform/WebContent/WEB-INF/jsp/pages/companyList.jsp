<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<h1>Company list</h1>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/company/json/admin/List.spr", "/company/admin/ChangeStatus.spr?id={id}");
		});
	</script>
</div>

<form action="/admin/Index.spr">
	<input type="submit" value="Back">
</form>
