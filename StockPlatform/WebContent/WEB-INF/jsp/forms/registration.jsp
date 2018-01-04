<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div style="display: table; width:100%" id="form-container">
	<div style="width: 40%;display: table-cell;">
		<div class="form-message"></div>
		<form class="form" action="#" method="post" name="form">
			<input type="hidden" name="latitude">
			<input type="hidden" name="longitude">
		    <ul>
		        <li>
		             <h2>${lable.registration.registration}</h2>
		        </li>
		        <li>
		            <label for="username">${lable.registration.username}</label>
		            <input type="email" name="username" placeholder="john_doe@example.com" required/>
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
		            <input type="text" name="name" placeholder="Stock lmt" required/>
		        </li>
		        <li>
		            <label for="phone">${lable.registration.phone}</label>
		            <input type="text" name="phone" placeholder="+375290000000" pattern="^\+375[\d\- ]{5,13}$" required/>
		            <span class="form_hint">Proper format "+375290000000"</span>
		        </li>
		        <li>
		            <label for="regNumber">${lable.registration.reg_number}</label>
		            <input type="text" name="regNumber" placeholder="1234567890" required/>
		            <span class="form_hint">Proper format "1234567890"</span>
		        </li>
		        <li>
		            <label for="country">${lable.registration.country}</label>
		            <input type="text" name="country" placeholder="Belarus" required/>
		        </li>
		        <li>
		            <label for="city">${lable.registration.city}</label>
		            <input type="text" name="city" placeholder="Minsk" required/>
		        </li>
		         <li>
		            <label for="address">${lable.registration.address}</label>
		            <input type="text" name="address" placeholder="Minsk" required/>
		        </li>
		        <li>
		        	<button class="submit" type="submit"
		        			onclick="
		        				return sendFormData($('#form-container'), 
			        				'/account/json/Registration.spr', 
			        				function(answer){
			        					processRequestResult(answer, $('.form-message'));
			        				});">${lable.button.save}</button>
		        </li>
		    </ul>
		</form>	
	</div>
	<div style="width: 5%; display: table-cell;"></div>
	<div style="width: 55%; height: 100%;position:relative; display: table-cell;">
		<div style="bottom:0;top: 0;right: 0; left: 0; position: absolute;">
			<div id="map" style="width: 100%; height: 100%"></div>
		</div>
	</div>
</div>

<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap"></script>