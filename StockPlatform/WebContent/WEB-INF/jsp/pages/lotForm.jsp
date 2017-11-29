
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<form action="/lot/Save.spr" method="post">
	
		<input type="hidden" name="id" value="${lot.id}"> 
		<input type="hidden" name="status" value="${lot.status}">

		${lable.lotForm.description}<input name="description" value="${lot.description}"><br>
		${lable.lotForm.volume}<input name="volume" value="${lot.volume}"><br>
		${lable.lotForm.min_volume}<input name="minVolume" value="${lot.minVolume }"><br>
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
				${lable.lotForm.product} - ${lot.productId}<br>		
				${lable.lotForm.min_price} - ${lot.minPrice}<br>
				${lable.lotForm.exp_date} - ${lot.dateStr}<br>
			</c:otherwise>
		</c:choose>
		<input type="submit" name="save" value="${lable.button.save}"><br> 
	</form>
	<br>
	
	<form action="/lot/StartTrading.spr">
		<input type="submit" value="${lable.lotForm.start_trading}">
	</form><br>
	
	<form action="/lot/Cancel.spr">
		<input type="submit" value="${lable.lotForm.cancel}">
	</form>
	<br>
	<form action="/lot/MyList.spr">
		<input type="submit" value="${lable.lotForm.my_lots}">
	</form>
