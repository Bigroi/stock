<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="div-tradeOffers">
		<form class="form form-tradeOffers" action="#" method="post" name="form">
		    <ul>
		        <li>
		            <input type="text" disabled value="${product.name}"/>
		        </li>
		        <li>
		            <input type="text" disabled value="${product.description}"/>
		        </li>
		    </ul>
		</form>
		<div class="table-tradeOffers">
			<table id = "main-table" data-url="/product/json/TradeOffers.spr?productId=${product.id}"></table>
		</div>
					
		<form class="form form-tradeOffers-button " action="/product/List.spr" method="post">
			<ul>
				<li>
					<button class="submit fs-submit" type="submit">${label.button.back}</button>
				</li>
			</ul>
		</form>
	</div>