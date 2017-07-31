
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
	Мои тендера
	<table border="1">
		<thead>
			<tr>
				<td>description</td>
				<td>productId</td>
				<td>max price</td>
				<td>customerId</td>
				<td>expDate</td>
				<td>status</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tender" items="${listOfTenders}">
				<tr>
					<td>${tender.description}</td>
					<td><select>
							<option disabled>choose product Id</option>
							<c:forEach var="tenderList" items="${listOfTenders}">
								<option>${tenderList.productId}</option>
							</c:forEach>
					</select></td>
					<td>${tender.maxPrice}</td>
					<td>${tender.customerId}</td>
					<td>${tender.dateStr}</td>
					<td>${tender.status}</td>

					<c:if test="${tender.status eq 'DRAFT'}">
						<td><form action="TenderInGameAuth.spr" method="get">
								<input type="hidden" name="id" value="${tender.id}"> <input
									type="submit" value="In game">
							</form></td>
					</c:if>
					<c:if
						test="${(tender.status eq 'DRAFT') || (tender.status eq 'IN_GAME')}">
						<td><form action="TenderFormAuth.spr" method="get">
								<input type="hidden" name="id" value="${tender.id}"> <input
									type="submit" value="Modify">
							</form></td>
						<td><form action="TenderCancelAuth.spr" method="get">
								<input type="hidden" name="id" value="${tender.id}"> <input
									type="submit" value="Cancel">
							</form></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<form action="TenderFormAuth.spr">
		<input type="hidden" name="id" value="-1" /> <input type="submit"
			value="Add tender">
	</form>
	<form action="Index.spr">
		<input type="submit" value="Welcome page">
	</form>
</body>
</html>