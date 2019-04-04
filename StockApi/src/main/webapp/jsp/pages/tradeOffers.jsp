<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div class="white-div">
		<div class="header-white-div">
			<form class="form form-tradeOffers" action="#" method="post" name="form">
			    <ul>
			    
			        <li>
			            <input type="text" disabled value="${product.name}"/>
			        </li>
			        
			        <%-- <c:if test="${product.name == 'Apple'}">
			        	<li>
			           		 <input type="text" disabled value="${label.description.aboutApple}"/>
			       		</li>
			        </c:if> --%>
			        
			         <c:if test="${product.name == 'Potato'}">
			        	<li>
			            	<input type="text" disabled value="${label.description.aboutPotato}"/>
			        	</li>
			        </c:if>
			        
			    </ul>
			</form>
		</div>
		
		<div class="table-tradeOffers">
			<table id = "main-table" data-url="/product/json/TradeOffers?productId=${product.id}"></table>
		</div>
					
		<form class="form form-tradeOffers-button " action="#" method="post">
			<ul>
				<li>
					<button class="submit fs-submit gray-button" type="submit" onclick="document.location = getContextRoot() + '/product/List'; return false;">${label.button.back}</button>
				</li>
			</ul>
		</form>
	</div>