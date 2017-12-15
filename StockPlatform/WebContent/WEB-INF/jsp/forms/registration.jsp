<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div style="display: table; width:100%">
	<div style="display: table-cell; width: 30%">
		<span style="color: red">${message}</span>
		<form class="form" action="/access/Registation.spr" method="post" name="form">
			<input type="hidden" name="latitude" value="${company.latitude}">
			<input type="hidden" name="longitude" value="${company.longitude}" >
		    <ul>
		        <li>
		             <h2>Contact Us</h2>
		             <span class="required_notification">* Denotes Required Field</span>
		        </li>
		        <li>
		            <label for="login">${lable.registration.login}</label>
		            <input type="email" name="login" placeholder="john_doe@example.com" required value="${user.login}"/>
		            <span class="form_hint">Proper format "john_doe@example.com"</span>
		        </li>
		        <li>
		            <label for="password">${lable.registration.password}</label>
		            <input type="password" name="password" required/>
		        </li>
		        <li>
		            <label for="password">${lable.registration.repeat_password}</label>
		            <input type="password" name="passwordRepeat" required/>
		        </li>
		        <li>
		            <label for="name">${lable.registration.company_name}</label>
		            <input type="text" name="name" placeholder="Stock lmt" required value="${company.name}"/>
		        </li>
		        <li>
		            <label for="phone">${lable.registration.phone}</label>
		            <input type="text" name="phone" placeholder="+375290000000" pattern="^\+375[\d\- ]{5,13}$" required value="${company.phone}"/>
		            <span class="form_hint">Proper format "+375290000000"</span>
		        </li>
		        <li>
		            <label for="regNumber">${lable.registration.reg_number}</label>
		            <input type="text" name="regNumber" placeholder="1234567890" required value="${company.regNumber}"/>
		            <span class="form_hint">Proper format "1234567890"</span>
		        </li>
		        <li>
		            <label for="country">${lable.registration.country}</label>
		            <input type="text" name="country" placeholder="Belarus" required value="${company.country}"/>
		        </li>
		        <li>
		            <label for="city">${lable.registration.city}</label>
		            <input type="text" name="city" placeholder="Minsk" required value="${company.city}"/>
		        </li>
		         <li>
		            <label for="address">${lable.registration.address}</label>
		            <input type="email" name="address" placeholder="address" required value="${company.address}"/>
		            <span class="form_hint">Proper format "john_doe@example.com"</span>
		        </li>
		        <li>
		        	<button class="submit" type="submit">${lable.button.save}</button>
		        </li>
		    </ul>
		</form>	
	</div>
	<div style="display: table-cell; width: 10%">
		
	</div>
	<div id="map" style="display: table-cell; width: 60%; hight:100%">
		
	</div>
</div>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap"></script>