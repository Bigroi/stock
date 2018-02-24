<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
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
	<br>
	<form action="/test/background/CheckStatus.spr">
		<input type="submit" value="Check exparation lots and tenders">
	</form>