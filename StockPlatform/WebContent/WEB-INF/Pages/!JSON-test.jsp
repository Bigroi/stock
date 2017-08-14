<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>TEST JSON:</h1>
	<p>Welcome ${user}</p>
	<c:if test="${user.login == null }">
		<p>Authenticate</p>
		<form action="AuthenticateJSON.spr">
			<input type="hidden" name="json"> Login: <input type="text"
				name="login"><br> Password<input type="password"
				name="password"><br> <input type="submit" value="enter">
		</form>
		<br>
	</c:if>
	<p>${message}
	<p>
	<p>------------------------------------------------------------------------------</p>
	<p>Regisration</p>

	<p>${message}</p>
	
	<form action="RegisrationJSON.spr" method="post">
		<input type="hidden" name="json"> Login:<input name="login"><br>
		Password:<input type="password" name="password"><br>
		Repeat Password:<input type="password" name="passwordRepeat"><br>
		name <input name="name"><br> email <input name="email"><br>
		phone <input name="phone"><br> reg_number <input
			name="regNumber"><br> country <input name="country"><br>
		city <input name="city"><br> <input type="submit"
			value="Save">
	</form>
	<p>------------------------------------------------------------------------------</p>
	<p>
		<a href="AccounPageAuthJSON.spr">AccounPageAuthJSON.spr</a>
	</p>
	<p>
		<a href="gotoAcc.spr">Account</a>
	</p>
	<p>------------------------------------------------------------------------------</p>
	<p>Test Lot</p>

	<form action="LotFormAuthJSON.spr">
		LotFormAuthJSON.spr(id)<input name="id" value="${lot.id}"> <input type="submit"
			value="press(lot.id)">
	</form>
	<form action="LotFormAuthJSON.spr">
		LotFormAuthJSON.spr(id)<input name="id" value="-1"> <input type="submit"
			value="press(id=-1)">
	</form><br>
	
	<fieldset>
	 <form action="LotSaveAuthJSON.spr">
	LotSaveAuth.spr(id...)<input name="id" value="${id}"><br>
	description<input type="text" name="description"><br>
	productId<input type="text" name="productId"><br>
	minPrice<input type="text" name="minPrice"><br>
	sellerId<input type="text" name="sellerId"><br>
	expDate<input type="text" name="expDate" ><br>
	status<input type="text" name="status">
	<input type="submit" value="press(id)">
	</form>
	</fieldset>
	
	<fieldset>
	 <form action="LotSaveAuthJSON.spr">
	LotSaveAuth.spr(id...)<input name="id" value="-1"><br>
	description<input name="description"><br>
	productId<input name="productId"><br>
	minPrice<input name="minPrice"><br>
	sellerId<input name="sellerId"><br>
	expDate<input name="expDate"><br>
	status<input name="status">
	<input type="submit" value="press(id=-1)">
	</form>
	</fieldset><br>
	
	<form action="LotInGameAuthJSON.spr">
	LotInGameAuthJSON.spr(id)<input type="text" name="id" value="${id}">
	<input type="submit" value="press(id)">
	</form>
	<form action="LotCancelAuthJSON.spr">
	LotCancelAuthJSON.spr(id)<input type="text" name="id" value="${id}">
	<input type="submit" value="press(id)">
	</form>
 


	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
</body>
</html>