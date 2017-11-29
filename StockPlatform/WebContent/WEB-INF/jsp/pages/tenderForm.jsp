
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<form action="/tender/Save.spr">
	
		<input type="hidden" name="id" value="${tender.id}"> 
		<input type="hidden" name="status" value="${tender.status}">

		${lable.tenderForm.description} <input name="description" value="${tender.description}"><br>
		${lable.tenderForm.volume} <input name="volume" value="${tender.volume}"><br>
		${lable.tenderForm.min_Volume } <input name="minVolume" value="${tender.minVolume }"><br>
		<c:choose>
			<c:when test="${tender.id == '-1'}">
				${lable.tenderForm.product}  <select name="productId">
					<option>choose product</option>
					<c:forEach var="product" items="${listOfProducts}">
						<option value="${product.id}">${product.name}</option>
					</c:forEach>
				</select><br> 
				${lable.tenderForm.max_price}  <input name="maxPrice" value="${tender.maxPrice}"><br>	    
			    ${lable.tenderForm.exp_date} <input name="expDate" value="${tender.dateStr}"><br>
			</c:when>
			<c:otherwise>
			    ${lable.tenderForm.product} - ${tender.productId}<br>
				${lable.tenderForm.max_price} - ${tender.maxPrice}<br>	    
			    ${lable.tenderForm.exp_date} - ${tender.dateStr}<br>
			</c:otherwise>
		</c:choose>
		<input type="submit" name="save" value="${lable.button.save}"><br> 
	</form>
	
	<form action="/tender/StartTrading.spr">
		<input type="hidden" name="id" value="${tender.id}"> 
		<input type="submit" value="${lable.tenderForm.start_trading}">
	</form>
	
	<br>
	<form action="/tender/Cancel.spr">
		<input type="hidden" name="id" value="${tender.id}"> 
		<input type="submit" value="${lable.tenderForm.cancel}">
	</form>
	<br>
	<form action="/tender/MyTenders.spr">
		<input type="submit" value="${lable.tenderForm.my_tenders}">
	</form>
