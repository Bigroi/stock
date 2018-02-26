<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<form class="form" action="#" method="post" name="form">
	    <ul>
	        <li>
	            <label for="name">${label.product.name}</label>
	            <input type="text" disabled value="${product.name}"/>
	        </li>
	        <li>
	            <label for="description">${label.product.description}</label>
	            <textarea cols="40" rows="6" disabled>${product.description}</textarea>
	        </li>
	    </ul>
	</form>

	<table id = "main-table"></table>
	<script>
		makeTable("/product/json/TradeOffers.spr?productId=${product.id}", $("#main-table"));
	</script>
				
	<form class="form" action="/product/List.spr" method="post">
		<ul>
			<li>
				<button class="submit" type="submit">${label.button.back}</button>
			</li>
		</ul>
	</form>