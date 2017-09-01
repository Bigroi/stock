<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>testBackgroundFunctions</title>
</head> 
<body>
	Status: ${message}<br>
	<form action="StartClearPreDeal.spr">
		<input type="submit" value="Start ClearPreDeal">
	</form>
	<br>
	<form action="StartTrade.spr">
		<input type="submit" value="Start Trade">
	</form>
	<br>
	<form action="StartSendEmail.spr">
		<input type="submit" value="Start SendEmail">
	</form>
</body>
</html>