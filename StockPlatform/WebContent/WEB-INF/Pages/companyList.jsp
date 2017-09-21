<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Company list</title>
</head>
<body>
	<p>Company list:</p>
	<table border="1" style="padding: 2px;">
		<thead>
			<tr style="padding: 2px;">
				<td>name</td>
				<td>email</td>
				<td>phone</td>
				<td>regNumber</td>
				<td>country</td>
				<td>city</td>
				<td>status</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="company" items="${listOfCompany}">
				<tr>
					<td>${company.name}</td>
					<td>${company.email}</td>
					<td>${company.phone}</td>
					<td>${company.regNumber}</td>
					<td>${company.country}</td>
					<td>${company.city}</td>
					<td>${company.status}</td>
					<td>
						<form action="ChangeStatus.spr">
							<input type="hidden" name="id" value="${company.id }"> <input
								type="submit" value="Change status">

						</form>
					</td>
					 <c:if test="${company.status eq 'REVOKED' }"> 
						<td>
							<form action="CancelApll.spr">
								<input type="hidden" name="id" value="${company.id}"> <input
									type="submit" value="Cancel all application" >
							</form>
						</td>
					 </c:if> 
				</tr>
			</c:forEach>
		</tbody>

	</table>
	<br>
	<form action="Index.spr">
		<input type="submit" value="Back">
	</form>
	
	
</body>
</html>