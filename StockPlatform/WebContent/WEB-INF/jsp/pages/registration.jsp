<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<p>${message}</p>
<form action="/access/Registation.spr" method="post">
	 ${lable.registration.login}<input name="login"><br> 
	 ${lable.registration.password}<input type="password" name="password"><br> 
	 ${lable.registration.repeat_password}<input type="password" name="passwordRepeat"><br> 
	
	 ${lable.registration.company_name} <input name="name" ><br>
	 ${lable.registration.email} <input name="email" ><br>
	 ${lable.registration.phone} <input name="phone" ><br>
	 ${lable.registration.reg_number} <input name="regNumber" ><br>
	 ${lable.registration.country} <input name="country" ><br>
	 ${lable.registration.city} <input name="city" ><br>
	
	<input type="submit" value="${lable.button.save}">
</form>
