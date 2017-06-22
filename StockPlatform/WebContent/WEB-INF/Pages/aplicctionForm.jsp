  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.bigroi.stock.bean.Product"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Application form</title>
</head>
<body>
<form action="ApplicationSave.do">
	<input type="hidden" <c:set var="Id" value="ApplicationID" />> <br>
		description <input name="description" value="${application.description}"><br>
		product <input name="product" value="${application.product}"><br>
		maxPrice  <input name="maxPrice" value="${application.maxPrice}"><br>
	    customer  <input name="customer" value="${application.customer}"><br>
	   <input type="submit" name="save" value="SAVE"><br> 
	<input type="button" name="back" value="BACK" onclick="document.location = 'ApplicationList.do'">
	</form>
	<form action="account.jsp">
		<input type="submit" value="Back">
	</form>
	<form action="myLotList.jsp">
		<input type="submit" value="Back">
	</form>
</body>
</html> 