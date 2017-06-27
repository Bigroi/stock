<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome page</title>
</head>
<body>
   <p> WELCOME !!! ${user.login}</p>
   <p><a href="LoginPage.spr">LOGIN</a> </p>
   <p><a href="RegistrationPage.spr">Registration</a> </p>
   <p><a href="AccounPageAuth.spr">Edit account</a> </p>
   <p><a href="ProductListPage.spr">Proluct list</a> </p>
   
  	<form action="ProductForm.spr">
		<input type="hidden" name="id" value="-1" />
		<input type="submit" value="Add product">
	</form>
	<form action="LotFormAuth.spr">
		<input type="hidden" name="id" value="-1" />
		<p><input type="submit" value="Add lot"></p>
	</form>
	<form action="TenderFormAuth.spr">
		<input type="hidden" name="id" value="-1" />
		<p><input type="submit" value="Add tender"></p>
	</form>
  </body>
</html>