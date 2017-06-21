

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>registration</title>
<link href="Style/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<p>${message}</p>
	<form action="Registation.spr" method="post">
	
		 Login:<input name="login"><br> 
		 Password:<input type="password" name="password"><br> 
		 Repeat Password:<input type="password" name="passwordRepeat"><br> 
		
		 name <input name="name" ><br>
		 email <input name="email" ><br>
		 phone <input name="phone" ><br>
		 reg_number <input name="regNumber" ><br>
		 country <input name="country" ><br>
		 city <input name="city" ><br>
		
		
		
		<input type="submit" value="Save">

	</form>

</body>
</html>
