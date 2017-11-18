<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<p>${message}</p>
<form action="/login" method="post">
		<p>${lable.login.login}:<input type="text" name="username"></p> 
		<p>${lable.login.password}:<input type="password" name="password"></p> 
		<input type="submit" value="${lable.button.login }">
</form>
