
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<form action="/lot/Save.spr" method="post">
	
		<input type="hidden" name="id" value="${lot.id}"> 
		<input type="hidden" name="status" value="${lot.status}">

		${lable.lotForm.description}<input name="description" value="${lot.description}"><br>
		${lable.lotForm.volume}<input name="volume" value="${lot.volume}"><br>
		<c:choose>
			<c:when test="${lot.id == '-1'}">
				${lable.lotForm.product}<select name="productId">
					<option>choose product</option>
					<c:forEach var="product" items="${listOfProducts}">
						<option value="${product.id}">${product.name}</option>
					</c:forEach>
				</select><br>                         	
				${lable.lotForm.min_price}<input name="minPrice" value="${lot.minPrice}"><br>
				${lable.lotForm.exp_date}<input name="expDate" value="${lot.dateStr}"><br>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="productId" value="${lot.productId}">
				<input type="hidden" name="minPrice" value="${lot.minPrice}">
				<input type="hidden" name="expDate" value="${lot.dateStr}">
				${lable.lotForm.product} - ${listOfProducts.get(lot.productId).name}<br>		
				${lable.lotForm.min_price} - ${lot.minPrice}<br>
				${lable.lotForm.exp_date} - ${lot.dateStr}<br>
			</c:otherwise>
		</c:choose>
		<input type="submit" name="save" value="${lable.button.save}"><br> 
	</form>
	
	<form action="/lot/MyList.spr">
		<input type="submit" value="${lable.lotForm.my_lots}">
	</form>
