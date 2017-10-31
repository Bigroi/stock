<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product list for Admin</title>
<script>
	function noDelete() {
		alert("Sorry, but it can't be temporarily deleted!");
		return false;
	}
</script>
</head>
<body>
<h2 style="color: red;"> Все продукты видит только АДМИН(log:Admin)</h2>
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
					<td>
						<a href="/product/Form.spr?id=${product.id}">
						${product.name}
						</a>
					</td>
					<td>${product.description}</td>
				</tr>
			</c:forEach>

		</tbody>

	</table>
	<br>
	<form action="/product/Form.spr" method="post">
		<input type="submit" value="Add">
	</form>
	<br>
	<form action="/admin/Index.spr" method="get">
		<input type="submit" value="Back">
	</form>

</body>
</html>