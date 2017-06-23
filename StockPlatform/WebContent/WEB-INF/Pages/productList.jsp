<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.bigroi.stock.bean.Product"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product list</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<td>name</td>
				<td>description</td>
				<td>lot count</td>
				<td>application count</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="product" items="${listOfProducts}">
				<tr>
					<td>${product.name}</td>
					<td>${product.description}</td>
	<!--НЕСООТВЕТСТВИЕ с бином и базой 			 -->	
					<td><%-- ${product.lotСount} --%></td>
					<td><%-- ${product.applicationСount} --%></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	 <form action="ProductForm.spr">
		<input type="hidden" name="id" value="-1" />
		<input type="submit" value="Add product">
	</form>
	<form action="Index.spr">
		<input type="submit" value="Back">
	</form>
</body>
</html>
