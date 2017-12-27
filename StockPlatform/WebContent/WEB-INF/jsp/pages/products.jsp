<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>${lable.products.title}</h1>
<div id = "lables">lable.product.name,lable.product.description</div>
<script>
$("#lables").localization();
</script>

<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/product/json/List.spr", "/product/TradeOffers.spr?id={id}");
		});
	</script>
</div>