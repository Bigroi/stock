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
	<table>
		<thead>
			<tr>
				<td>name</td>
				<td>description</td>
				<td>lot count</td>
				<td>application count</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="product" items="${ProductsofList}">
				<tr>
					<td>${product.name}</td>
					<td>${product.description}</td>
					<td>${product.lotcount}</td>
					<td>${product.applicationcount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form action="welcome.jsp">
		<input type="submit" value="Back">
	</form>
</body>
</html>
