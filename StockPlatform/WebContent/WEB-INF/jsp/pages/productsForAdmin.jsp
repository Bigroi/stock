<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/product/json/admin/List.spr", "/product/admin/Form.spr?id={id}");
		});
	</script>
</div>
<form action="/product/admin/Form.spr" method="post">
	<input type="submit" value="${lable.button.create}">
</form>
<br>
