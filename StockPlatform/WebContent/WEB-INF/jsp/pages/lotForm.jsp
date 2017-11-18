
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<form action="/lot/Save.spr" method="post">
	
		 <input type="hidden" name="id" value="${lot.id}"> 
		 <input type="hidden" name="status" value="${lot.status}">

		description <input name="description" value="${lot.description}"><br>
		volume of lot  <input name="volume" value="${lot.volume}"><br>
		<c:choose>
			<c:when test="${lot.id == '-1'}">
				product id  <select name="productId">
					<option>choose product</option>
					<c:forEach var="product" items="${listOfProducts}">
						<option value="${product.id}">${product.name}</option>
					</c:forEach>
				</select><br>                         	
				min_price  <input name="minPrice" value="${lot.minPrice}"><br>
				exp_date  <input name="expDate" value="${lot.dateStr}"><br>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="productId" value="${lot.productId}">
				<input type="hidden" name="minPrice" value="${lot.minPrice}">
				<input type="hidden" name="expDate" value="${lot.dateStr}">
				product id - ${listOfProducts.get(lot.productId).name}<br>		
				min_price - ${lot.minPrice}<br>
				exp_date - ${lot.dateStr}<br>
			</c:otherwise>
		</c:choose>
		<input type="submit" name="save" value="SAVE"><br> 
		<input type="button" name="back" value="Welcome page" onclick="document.location = '/Index.spr'">
	</form>
	
	<form action="/lot/MyList.spr">
		<input type="submit" value="My list of lots">
	</form>
