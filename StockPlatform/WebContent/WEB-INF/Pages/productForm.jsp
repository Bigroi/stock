<!--
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

	<form action="ProductSave.do">
	
		<input type="hidden" <c:set var="Id" value="ProductID" />> <br>
		
		name <input name="name" value="${product.name}"><br>
		
		description <input name="description" value="${product.description}"><br>
		

		<input type="submit" name="save" value="SAVE"><br> 
		<input type="button" name="back" value="BACK" onclick="document.location = 'ProductList.do'">
	</form>
	<form action="welcome.jsp">
		<input type="submit" value="Back">
	</form>

</body>
</html>
-->