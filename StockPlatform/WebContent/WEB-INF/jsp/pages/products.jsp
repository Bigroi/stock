<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>${label.products.title}</h1>

<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/product/json/List.spr", "/product/TradeOffers.spr?id={id}");
		});
	</script>
</div>