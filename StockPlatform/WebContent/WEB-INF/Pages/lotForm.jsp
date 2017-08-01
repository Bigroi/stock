
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

	<form action="LotSaveAuth.spr" method="get">
	
		 <input type="hidden" name="id" value="${id}"> 
		 <input type="hidden" name="sellerId" value="${lot.sellerId}">
		 <input type="hidden" name="status" value="${lot.status}">

		description <input name="description" value="${lot.description}"><br>
		<c:choose>
			<c:when test="${id == '-1'}">
				product id  <select name="productId">
					<option>choose product</option>
					<c:forEach var="product" items="${listOfProducts}">
						<option>${product.name}</option>
					</c:forEach>
				</select><br>                         	
				min_price  <input name="minPrice" value="${lot.minPrice}"><br>
				exp_date  <input name="expDate" value="${lot.dateStr}"><br>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="productId" value="${lot.poductId}">
				<input type="hidden" name="minPrice" value="${lot.minPrice}">
				<input type="hidden" name="expDate" value="${lot.dateStr}">
				product id - ${lot.poductId}<br>		
				min_price - ${lot.minPrice}<br>
				exp_date - ${lot.dateStr}<br>
			</c:otherwise>
		</c:choose>
		seller id - ${lot.sellerId}<br> 
		status - ${lot.status}<br> 
		<input type="submit" name="save" value="SAVE"><br> 
		<input type="button" name="back" value="Welcome page" onclick="document.location = 'Index.spr'">
	</form>
	
	<form action="MyLotListAuth.spr">
		<input type="submit" value="My list of lots">
	</form>
	
</body>
</html>
