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
	Мои лоты
	<table border="1">
		<thead>
			<tr>
				<td>description</td>
				<td>poductId</td>
				<td>min price</td>
				<td>sellerId</td>
				<td>expDate</td>
				<td>status</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="lot" items="${listOfLots}">
				<tr>
					<td>${lot.description}</td>
					<td>${lot.poductId}</td>
					<td>${lot.minPrice}</td>
					<td>${lot.sellerId}</td>
					<td>${lot.dateStr}</td>
					<td>${lot.status}</td>

					<c:if test="${lot.status eq 'DRAFT'}">
						<td><form action="LotInGameAuth.spr" method="get">
								<input type="hidden" name="id" value="${lot.id}"> <input
									type="submit" value="In game">
							</form></td>
					</c:if>
					<c:if
						test="${(lot.status eq 'DRAFT') || (lot.status eq 'IN_GAME')}">
						<td><form action="LotFormAuth.spr" method="get">
								<input type="hidden" name="id" value="${lot.id}"> <input
									type="submit" value="Modify">
							</form></td>
						<td><form action="LotCancelAuth.spr" method="get">
								<input type="hidden" name="id" value="${lot.id}"> <input
									type="submit" value="Cancel">
							</form></td>
					</c:if>

				</tr>
			</c:forEach>
		</tbody>
	</table>

	<form action="LotFormAuth.spr">
		<input type="hidden" name="id" value="-1" /> <input type="submit"
			value="Add lot">
	</form>
	<form action="Index.spr">
		<input type="submit" value="Welcome page">
	</form>
</body>
</html>