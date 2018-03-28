<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<form class="form form-tradeOffers" action="#" method="post" name="form">
	    <ul>
	        <li>
	           <!--<label for="name">${label.product.name}</label>-->
	            <input type="text" disabled value="${product.name}"/>
	        </li>
	        <li>
	           <!--<label for="description">${label.product.description}</label>-->
	            <input type="text" disabled value="${product.description}"/>
	        </li>
	    </ul>
	</form>
	<div class="table-tradeOffers">
		<table id = "main-table" ></table>
	</div>
	<div id="chart" class="chart"></div>
	<script>
		makeTable("/product/json/TradeOffers.spr?productId=${product.id}", $("#main-table"));
		buildChart("chart", "/product/json/TradeOfferChart.spr", {"productId":${product.id}});
	</script>
				
	<form class="form form-tradeOffers-button " action="/product/List.spr" method="post">
		<ul>
			<li>
				<button class="submit fs-submit" type="submit">${label.button.back}</button>
			</li>
		</ul>
	</form>