<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1>TEST JSON:</h1>
<p>Welcome ${user}</p>
<p>Authenticate</p>
<form action="AuthenticateJSON.spr">
<input type="hidden" name="json">
Login: <input type="text" name="login"><br> 
		Password<input type="password" name="password"><br> 
			<input type="submit" value="enter">

</form><br>

<p>${message}<p>

<p>Regisration</p>

<p>${message}</p>
	
	<form action="RegisrationJSON.spr" method="post">
	<input type="hidden" name="json"> 
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
	
	<p>AccountChangeAuth</p>
	
	<form action="AccountChangeAuthJSON.spr" method="post">
		<p>${user.login}</p>
		<input type="hidden" name="json">
		<input type="hidden" name="status" value="${company.status}">
		 <p>password <input name="password" value="${user.password}"></p>
		 name <input name="name" value="${company.name}"><br>
		 email <input name="email" value="${company.email}"><br>
		 phone <input name="phone" value="${company.phone}"><br>
		 reg_number <input name="regNumber" value="${company.regNumber}"><br>
		 country <input name="country" value="${company.country}"><br>
		 city <input name="city" value="${company.city}"><br>
		 status - ${company.status}<br>		
		<input type="submit" value="MODIFY">
	</form>

</body>
</html>