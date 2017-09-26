<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="ProdSave.spr">
		<input type="hidden" name="id" value="${getId}"> <br>

		name <input name="name" value="${product.name}"><br>

		description <input name="description" value="${product.description}"><br>
		<input type="submit" name="save" value="SAVE"><br> 
	</form>
	<form action="ProductListAdmin.spr">
		<input type="submit" value="Back">
	</form>
</body>
</html>