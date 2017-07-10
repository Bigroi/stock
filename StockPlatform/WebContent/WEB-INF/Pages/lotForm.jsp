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

	<form action="LotSaveAuth.spr">
	
		<input type="hidden" name="id" value="${id}"> 
		<input type="hidden" name="sellerId" value="${lot.sellerId}">
		<input type="hidden" name="status" value="${lot.status}">
		
		description <input name="description" value="${lot.description}"><br>
		product id <input name="productId" value="${lot.poductId}"><br>
		min_price  <input name="minPrice" value="${lot.minPrice}"><br>
	    seller id - ${lot.sellerId}<br>
	    exp_date  <input name="expDate" value="${lot.dateStr}"><br>
	    status - ${lot.status}<br>
		

		<input type="submit" name="save" value="SAVE"><br> 
		<input type="button" name="back" value="Welcome page" onclick="document.location = 'Index.spr'">		
	</form>
	<form action="MyLotListAuth.spr">
		<input type="submit" value="My list of lots">
	</form>

</body>
</html> 