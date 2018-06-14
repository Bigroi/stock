<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<form class="form" action="#" method="post" name="form" id="login-form">
		<h3>${label.registration.registration}</h3>
		<p>${label.registration.haveAcc}<a href=""><span>${label.registration.logHere}</span></a></p>
		<div>
			<div class="dialogbox-message"></div>
		</div>
		<div class="registration-first-part">
			<div class="flex-input">
			<div>
				<label for="forLogin">${label.registration.login}</label>
				<input type="email" name="username" id="forLogin" placeholder="john_doe@example.com" required maxlength="50">
				<!--<span class="form_hint">Proper format "john_doe@example.com"</span>  -->
			</div>
			<div>
				<label for="forPhone">${label.registration.phone}</label>
				<input type="text" name="company.phone" placeholder="+375290000000" pattern="^\+375[\d\- ]{5,13}$" required/>
		        <!--<span class="form_hint">Proper format "+375290000000"</span> -->
			</div>
			<div>
	            <label for="ForCompanyName">${label.registration.company_name}</label>
	            <input type="text" name="company.name" placeholder="Stock lmt" id="ForCompanyName" required maxlength="100"/>
		     </div>
		     <div>
	            <label for="forRegNumber">${label.registration.reg_number}</label>
	            <input type="text" name="company.regNumber" placeholder="1234567890" id="forRegNumber" required maxlength="100"/>
	            <!--<span class="form_hint">Proper format "1234567890"</span>-->
		     </div>
			<div>
				<label for="forPassword">${label.registration.password}</label>
				<input type="password" name="password" id="forPassword" placeholder="***********" required maxlength="50">
			</div>
			<div>
				<label for="forPasswordAgain">${label.registration.repeat_password}</label>
				<input type="password" name="passwordRepeat" id="forPasswordAgain" placeholder="***********" required maxlength="50">
			</div>
		 </div>
		 <button type="button" class="button" id="continue">${label.button.continueButton}</button>
	     <p class="second-step">${label.registration.secondStepReg}</p>
		</div>
		<div class="registration-second-part">
			<div class="flex-input">
				<div>
					<label for="forCountry">${label.registration.city}</label>
		            <input type="text" name="company.address.city" class="city" id="forCountry" placeholder="Minsk" required maxlength="100"/>
				</div>
				<div>
					<label for="forCity">${label.registration.country}</label>
		            <input type="text" name="company.address.country" class="country" id="forCity" placeholder="Belarus" required maxlength="50"/>
				</div>
				<div>
					<label for="forAdress">${label.registration.address}</label>
		            <input type="text" name="company.address.address" class="address" id="forAdress" placeholder="Minsk" required maxlength="200"/>
				</div>
			</div>
			<button type="button" id="go-back">${label.button.back}</button>
			<button type="submit" class="submit" id="finish-registration">${label.button.finishRegistration}</button>
		
		</div>
		
	</form>
<!--<div style="display: table; width:100%" id="form-container">
	<div style="width: 40%;display: table-cell;" id="form-container">
		<div class="form-message"></div>
		<form class="form" action="#" method="post" name="form">
			<input type="hidden" name="company.address.latitude" class="latitude">
			<input type="hidden" name="company.address.longitude" class="longitude">
		    <ul>
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
	
</div>  -->

<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap"></script>