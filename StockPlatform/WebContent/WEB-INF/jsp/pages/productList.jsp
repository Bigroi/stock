<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>Products</h1>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/product/json/List.spr", "/product/TradeOffers.spr?id={id}");
		});
	</script>
</div>
<form action="/Index.spr">
	<input type="submit" value="Welcome page">
</form>
