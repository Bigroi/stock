
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product form</title>
</head>
<body>

	<form action="/lot/Save.spr" method="post">
	
		 <input type="hidden" name="id" value="${lot.id}"> 
		 <input type="hidden" name="sellerId" value="${lot.sellerId}">
		 <input type="hidden" name="status" value="${lot.status}">

		description <input name="description" value="${lot.description}"><br>
		volume of lot  <input name="volumeOfLot" value="${lot.volumeOfLot}"><br>
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
				<!-- volume_of_lot  <input name="volumeOfLot" value="${lot.volumeOfLot}"><br> -->
			</c:when>
			<c:otherwise>
				<input type="hidden" name="productId" value="${lot.poductId}">
				<input type="hidden" name="minPrice" value="${lot.minPrice}">
				<input type="hidden" name="expDate" value="${lot.dateStr}">
				<!--  <input type="hidden" name="volumeOfLot" value="${lot.volumeOfLot}"><br>-->
				product id - ${lot.poductId}<br>		
				min_price - ${lot.minPrice}<br>
				exp_date - ${lot.dateStr}<br>
				<!--volume of lot - ${lot.volumeOfLot}<br>-->
			</c:otherwise>
		</c:choose>
		seller id - ${lot.sellerId}<br> 
		status - ${lot.status}<br> 
		<input type="submit" name="save" value="SAVE"><br> 
		<input type="button" name="back" value="Welcome page" onclick="document.location = 'Index.spr'">
	</form>
	
	<form action="/lot/MyList.spr">
		<input type="submit" value="My list of lots">
	</form>
	
</body>
</html>
