<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Change User Password</title>
</head>
<body>
	<p>User List</p>
	<table border="1">
			<thead>
				<tr>
					<td>Name User</td>
					<td>Button</td>				
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${listOfUser}">
					<tr>
						<td>${user.login}</td>
						<td>
							<form action="/user/admin/ResetPassword.spr" method="get">
									<input type="hidden" name="login" value="${user.login}">   
									<input type="submit" value="Generate New Pass">
								</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		 <form action="/admin/Index.spr">
			<input type="submit" value="Welcome page">
		</form>
</body>
</html>