 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="display: table; width:100%">
	<div style="display: table-cell; width: 30%">
		<form action="/account/Save.spr" method="post">
			<input type="hidden" name="status" value="${company.status}">
			<p>${lable.account.password} <input name="password" type="password"></p>
			<p>${lable.account.name} <input disabled="disabled" name="name" value="${company.name}"></p>
			<p>${lable.account.email} <input name="email" value="${company.email}"></p>
			<p>${lable.account.phone} <input name="phone" value="${company.phone}"><br>
			<p>${lable.account.reg_number} <input disabled="disabled" name="regNumber" value="${company.regNumber}"><br>
			<p>${lable.account.country} <input name="country" value="${company.country}"><br>
			<p>${lable.account.city} <input name="city" value="${company.city}"><br>
			<input type="hidden" name="width" value="${company.width}">
			<input type="hidden" name="length" value="${company.length}" >
			<input type="submit" value="${lable.button.modify }">
		</form>
	</div>
	<div style="display: table-cell; width: 10%">
		
	</div>
	<div id="map" style="display: table-cell; width: 60%; hight:100%">
		
	</div>
</div>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap"></script>