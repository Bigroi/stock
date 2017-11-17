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
	<form action="/test/background/ClearPreDeals.spr">
		<input type="submit" value="Start ClearPreDeal">
	</form>
	<br>
	<form action="/test/background/Trading.spr">
		<input type="submit" value="Start Trade">
	</form>
	<br>
	<form action="/test/background/SendEmails.spr">
		<input type="submit" value="Start SendEmail">
	</form>
	<form action="/test/background/CheckStatus.spr">
		<input type="submit" value="Check exparation lots and tenders">
	</form>
	<form action="/test/background/sendConfMessages.spr">
		<input type="submit" value="Send Conformation Messages">
	</form>
	
	<a href="/Index.spr">Index</a>
	<a href="/admin/Index.spr">Admin page</a>
</body>
</html>