<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<p>${message}</p>
<form action="/login" method="post">
		<p>Login:<input type="text" name="username"></p> 
		<p>Password:<input type="password" name="password"></p> 
		<input type="submit" value="LOGIN">
</form>
