<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.bigroi.stock.bean.common.CompanyStatus"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Change status</title>
</head>
<body>
	<p>
		<strong>available statuses: NOT_VERIFIED, VERIFIED, REVOKED</strong>
	<p>
		
	<form action="SaveStatus.spr">
		<input type="hidden" name="id" value="${listOfStatus}"> change
		status:<input name="status" value="${company.status}"> <input
			type="submit" value="Save">
	</form>
	<br>

	<form action="CompanyList.spr">
		<input type="submit" value="Back">
	</form>
	<br>

	<form action="Index.spr">
		<input type="submit" value="Weclom page">
	</form>
	<br>

	<form action="SaveStatus.spr">
		<input type="hidden" name="id" value="${listOfStatus}"> 
		<select name="status">
			<c:forEach var="companyArray" items="${FullListOfStatus }">
				<option  value="${company.status}">${companyArray.status}</option>
			</c:forEach>
		</select> <input type="submit" value="Save2">
	</form>

</body>
</html>