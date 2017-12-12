<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div style="display: table; width:100%">
	<div style="display: table-cell; width: 30%">
		<span style="color: red">${message}</span>	
		<form action="/access/Registation.spr" method="post">
			 ${lable.registration.login}<input name="login" value="${user.login}"><br> 
			 ${lable.registration.password}<input type="password" name="password"><br> 
			 ${lable.registration.repeat_password}<input type="password" name="passwordRepeat"><br> 
			
			 ${lable.registration.company_name} <input name="name" value="${company.name}"><br>
			 ${lable.registration.email} <input name="email" value="${company.email}"><br>
			 ${lable.registration.phone} <input name="phone" value="${company.phone}"><br>
			 ${lable.registration.reg_number} <input name="regNumber" value="${company.regNumber}"><br>
			 ${lable.registration.country} <input name="country" value="${company.country}"><br>
			 ${lable.registration.city} <input name="city" value="${company.city}"><br>
			<input type="hidden" name="latitude" value="${company.latitude}">
			<input type="hidden" name="longitude" value="${company.longitude}" >
			
			<input type="submit" value="${lable.button.save}">
		</form>
	</div>
	<div style="display: table-cell; width: 10%">
		
	</div>
	<div id="map" style="display: table-cell; width: 60%; hight:100%">
		
	</div>
</div>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap"></script>