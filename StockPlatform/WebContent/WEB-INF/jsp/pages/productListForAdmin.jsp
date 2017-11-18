<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<p style="color: red;"> Все продукты видит только АДМИН (log:Admin)!!!</p>
<h1>Product list:</h1>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/product/json/admin/List.spr", "/product/admin/Form.spr?id={id}");
		});
	</script>
</div>
<form action="/product/admin/Form.spr" method="post">
	<input type="submit" value="Add">
</form>
<br>
<form action="/admin/Index.spr" method="get">
	<input type="submit" value="Back">
</form>
