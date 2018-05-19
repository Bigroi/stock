<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div style="display: table; width:100%" id="form-container">
	<div style="width: 40%;display: table-cell;" id="form-container">
		<div class="form-message"></div>
		<form class="form" action="#" method="post" name="form">
			<input type="hidden" name="company.address.latitude" class="latitude">
			<input type="hidden" name="company.address.longitude" class="longitude">
		    <ul>
		    	<li></li>
		        <li>
		            <label for="username">${label.registration.login}</label>
		            <input type="email" name="username" placeholder="john_doe@example.com" required maxlength="50"/>
		            <span class="form_hint">Proper format "john_doe@example.com"</span>
		        </li>
		        <li>
		            <label for="password">${label.registration.password}</label>
		            <input type="password" name="password" required maxlength="50"/>
		        </li>
		        <li>
		            <label for="password">${label.registration.repeat_password}</label>
		            <input type="password" name="passwordRepeat" required maxlength="50"/>
		        </li>
		        <li>
		            <label for="name">${label.registration.company_name}</label>
		            <input type="text" name="company.name" placeholder="Stock lmt" required maxlength="100"/>
		        </li>
		        <li>
		            <label for="phone">${label.registration.phone}</label>
		            <input type="text" name="company.phone" placeholder="+375290000000" pattern="^\+375[\d\- ]{5,13}$" required/>
		            <span class="form_hint">Proper format "+375290000000"</span>
		        </li>
		        <li>
		            <label for="regNumber">${label.registration.reg_number}</label>
		            <input type="text" name="company.regNumber" placeholder="1234567890" required maxlength="100"/>
		            <span class="form_hint">Proper format "1234567890"</span>
		        </li>
		        <li>
		            <label for="country">${label.registration.country}</label>
		            <input type="text" name="company.address.country" class="country" placeholder="Belarus" required maxlength="50"/>
		        </li>
		        <li>
		            <label for="city">${label.registration.city}</label>
		            <input type="text" name="company.address.city" class="city" placeholder="Minsk" required maxlength="100"/>
		        </li>
		         <li>
		            <label for="address">${label.registration.address}</label>
		            <input type="text" name="company.address.address" class="address" placeholder="Minsk" required maxlength="200"/>
		        </li>
		        <li>
		        	<button class="submit" type="submit" id="save-button"
		        			onclick="
		        				return sendFormData($('#form-container > form'), 
				        				function(formContainer, param){
			        						$.post('/account/json/Registration.spr', param, function(answer){
			        							processRequestResult($('#form-container > form'), answer, $('.form-message'));
			        							if (answer.result > 0){
			        								$('#save-button').remove();
			        							}
			        						});
			        					});">${label.button.save}</button>
		        </li>
		    </ul>
		</form>	
	</div>
	<div style="width: 5%; display: table-cell;"></div>
	<div style="width: 55%; height: 100%;position:relative; display: table-cell;" id="map-mob-reg">
		<div style="bottom:0;top: 0;right: 0; left: 0; position: absolute;">
			<div id="map" style="width: 100%; height: 100%"></div>
		</div>
	</div>
</div>

<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap"></script>