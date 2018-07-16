<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="#" method="post" name="form" id="registration-form">
	<input type="hidden" name="latitude" class="latitude">
	<input type="hidden" name="longitude" class="longitude"> 
	<input type="hidden" name="id">
	
	<h3>${label.address.title}</h3>
	<div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="flex-input">
		<div>
			<label for="forCountry">${label.address.country} *</label> 
			<input type="text" name="country" class="country" id="forCountry" required maxlength="50">
		</div>
		<div>
			<label for="forCity">${label.address.city} *</label>
			<input type="text" name="city" class="city" id="forCity" required maxlength="50"/>
		</div>
		<div>
			<label for="forAddress">${label.address.address} *</label> 
			<input type="text" name="address" class="address" id="forAddress" required maxlength="50"/>
		</div>

		<div class="forMap google-map-container"></div>
		<div id="form-list"></div>
	</div>
</form>

<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initRegistrationMap"></script>