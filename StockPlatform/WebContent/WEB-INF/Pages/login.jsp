<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>${message}</p>
<form action="/login" method="post">
		<p>Login:<input type="text" name="username"></p> 
		<p>Password:<input type="password" name="password"></p> 
			<input type="submit" value="LOGIN">

	</form>
</body>
</html>