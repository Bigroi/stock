<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>Product name: ${product.name}</p>
	<p>Description: ${product.description}</p>
	<table>
		<tbody>
			<tr>
				<td>Lots: <br>
					<table border="1">
						<thead>
							<tr>
								<td>Price</td>
								<td>Exp date</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="lot" items="${listOfLots}">
								<tr>
									<td>${lot.minPrice}</td>
									<td>${lot.dateStr}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
				<td>Tenders: <br>
					<table border="1">
						<thead>
							<tr>
								<td>Price</td>
								<td>Exp date</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="tender" items="${listOfTenders}">
								<tr>
									<td>${tender.maxPrice}</td>
									<td>${tender.dateStr}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table></td>
			</tr>
		</tbody>
	</table>
	<form action="/product/List.spr">
		<input type="submit" value="Product list">
	</form>
	<form action="/Index.spr">
		<input type="submit" value="Welcome page">
	</form>
</body>
</html>