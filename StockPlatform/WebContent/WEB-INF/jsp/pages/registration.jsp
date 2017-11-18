<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<p>${message}</p>
<form action="/access/Registation.spr" method="post">
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
