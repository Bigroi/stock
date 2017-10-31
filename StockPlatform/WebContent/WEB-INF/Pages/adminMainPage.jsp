<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Main Page</title>
</head>
<body>
${message}
	<form action="/user/admin/ChangeUserPass.spr">
		<input type="submit" value="ChangeUserPass.spr">
	</form>
	<form action="/product/admin/List.spr">
		<input type="submit" value="ProductListAdmin.spr">
	</form>
	<form action="/company/admin/List.spr">
		<input type="submit" value="CompanyList.spr">
	</form>
</body>
</html>