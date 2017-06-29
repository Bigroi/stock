
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product form</title>
</head>
<body>

	<form action="ProductSave.spr">
	
		<input type="hidden" name="id" value="${id}"> <br>
		
		name <input name="name" value="${product.name}"><br>
		
		description <input name="description" value="${product.description}"><br>
		

		<input type="submit" name="save" value="SAVE"><br> 
		<input type="button" name="back" value="Welcome page" onclick="document.location = 'Index.spr'">
	</form>
	<form action="ProductListPage.spr">
		<input type="submit" value="List of products">
	</form>

</body>
</html>