  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Application form</title>
</head>
<body>
<form action="TenderSaveAuth.spr">
	<input type="hidden" name="id" value="${id}">
	<input type="hidden" name="customerId" value="${tender.customerId}">
	<input type="hidden" name="status" value="${tender.status}">
		
		description <input name="description" value="${tender.description}"><br>
		product id <input name="productId" value="${tender.productId}"><br>
		maxPrice  <input name="maxPrice" value="${tender.maxPrice}"><br>
	    customer id - ${tender.customerId}<br>
	    expDate <input name="expDate" value="${tender.dateStr}"><br>
	    status - ${tender.status}<br>
	    
	    
	   <input type="submit" name="save" value="SAVE"><br> 
	<input type="button" name="back" value="Welcome page" onclick="document.location = 'Index.spr'">
	</form>
	<form action="MyTenderListAuth.spr">
		<input type="submit" value="My list of Tenders">
	</form>
</body>
</html> 