<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
	Status: ${message}<br>
	<form action="#">
		<input type="submit" value="Start ClearPreDeal" 
			onclick="document.location = getContextRoot() + '/test/background/ClearPreDeals.spr'">
	</form>
	<br>
	<form action="#">
		<input type="submit" value="Start Trade" 
			onclick="document.location = getContextRoot() + '/test/background/Trading.spr'">
	</form>
	<br>
	<form action="#">
		<input type="submit" value="Start SendEmail" 
			onclick="document.location = getContextRoot() + '/test/background/SendEmails.spr'">
	</form>
	<br>
	<form action="#">
		<input type="submit" value="Check exparation lots and tenders" 
			onclick="document.location = getContextRoot() + '/test/background/CheckStatus.spr'">
	</form>