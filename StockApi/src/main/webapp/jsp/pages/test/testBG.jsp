<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
	Status: ${message}<br>
	<form action="#">
		<input type="submit" value="Start ClearPreDeal" 
			onclick="document.location = getContextRoot() + '/test/background/ClearPreDeals'; return false;">
	</form>
	<br>
	<form action="#">
		<input type="submit" value="Start Trade" 
			onclick="document.location = getContextRoot() + '/test/background/Trading'; return false;">
	</form>
	<br>
	<form action="#">
		<input type="submit" value="Start SendEmail" 
			onclick="document.location = getContextRoot() + '/test/background/SendEmails'; return false;">
	</form>
	<br>
	<form action="#">
		<input type="submit" value="Check exparation lots and tenders" 
			onclick="document.location = getContextRoot() + '/test/background/CheckStatus'; return false;">
	</form>