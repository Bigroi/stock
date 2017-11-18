
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<form action="/tender/Save.spr">
	
		<input type="hidden" name="id" value="${tender.id}"> 
		<input type="hidden" name="status" value="${tender.status}">

		description <input name="description" value="${tender.description}"><br>
		volume of tender  <input name="volume" value="${tender.volume}"><br>
		<c:choose>
			<c:when test="${tender.id == '-1'}">
				product id  <select name="productId">
					<option>choose product</option>
					<c:forEach var="product" items="${listOfProducts}">
						<option value="${product.id}">${product.name}</option>
					</c:forEach>
				</select><br> 
				maxPrice  <input name="maxPrice" value="${tender.maxPrice}"><br>	    
			    expDate <input name="expDate" value="${tender.dateStr}"><br>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="productId" value="${tender.productId}">
				<input type="hidden" name="maxPrice" value="${tender.maxPrice}">
				<input type="hidden" name="expDate" value="${tender.dateStr}">
			    product id - ${listOfProducts.get(tender.productId).name}<br>
				maxPrice - ${tender.maxPrice}<br>	    
			    expDate - ${tender.dateStr}<br>
			</c:otherwise>
		</c:choose>
		<input type="submit" name="save" value="SAVE"><br> 
		<input type="button" name="back" value="Welcome page" onclick="document.location = '/Index.spr'">
	</form>
	
	<form action="/tender/MyList.spr">
		<input type="submit" value="My list of Tenders">
	</form>
