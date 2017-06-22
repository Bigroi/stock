  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.bigroi.stock.bean.Product"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product form</title>
</head>
<body>

	<form action="lotSave.do">
	
		<input type="hidden" <c:set var="Id" value="lotID" />> <br>
		
		description <input name="description" value="${lot.description}"><br>
		product <input name="product" value="${lot.product}"><br>
		min_price  <input name="min_price" value="${lot.min_price}"><br>
	    saler  <input name="saler" value="${lot.saler}"><br>
	    exp_date  <input name="exp_date" value="${lot.exp_date}"><br>
		

		<input type="submit" name="save" value="SAVE"><br> 
		<input type="button" name="back" value="BACK" onclick="document.location = 'LotList.do'">
	</form>
	
	<form action="account.jsp">
		<input type="submit" value="Back">
	</form>
	
	<form action="myLotList.jsp">
		<input type="submit" value="Back">
	</form>

</body>
</html> 