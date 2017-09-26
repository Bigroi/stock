<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product list for Admin</title>
</head>
<body>
	<p>Product list:</p>
	<table border="2">
		<thead>
			<tr>
				<td>name</td>
				<td>description</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="product" items="${listOfProducts}">
				<tr>
					<td>${product.name}</td>
					<td>${product.description}</td>
					<td>
						<form action="EditProduct.spr" method="get">
							<input type="hidden" name="id" value="${product.id}"> <input
								type="submit" value="edit">
						</form>
					</td>

					<td>
						<form action="DeleteProduct.spr" method="get">
							<input type="hidden" name="id" value="${product.id}"> <input
								type="submit" value="delete">
						</form>
					</td>

				</tr>
			</c:forEach>

		</tbody>

	</table>
	<br>
	<form action="EditProduct.spr" method="get">
		<input type="hidden" name="id" value="-1"> <input
			type="submit" value="add">
	</form><br>
	
	<form action="Index.spr">
		<input type="submit" value="Back">
	</form>
</body>
</html>