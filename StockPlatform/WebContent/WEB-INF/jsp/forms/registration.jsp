<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form" id="registration-form">
	<input type="hidden" name="company.address.latitude" class="latitude">
	<input type="hidden" name="company.address.longitude" class="longitude">
	<h3>${label.account.registration}</h3>
	<p>${label.account.haveAcc}<span class="go_to_login" onclick="openLoginForm();"><span>${label.account.logHere}</span></span></p>
	<div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="registration-first-part">
		<div class="flex-input">
		<div>
			<label for="forLogin">${label.account.login}</label>
			<input type="email" name="username" id="forLogin" placeholder="john_doe@example.com" required maxlength="50">
		</div>
		<div>
			<label for="forPhone">${label.account.phone}</label>
			<input type="text" name="company.phone" 
				placeholder="${label.account.phone_placeholder}" 
				pattern="${label.account.phone_pattern}" required/>
		</div>
		<div>
            <label for="forCompanyName">${label.account.company_name}</label>
            <input type="text" name="company.name" 
            	placeholder="Stock ltd" id="forCompanyName" required maxlength="100"/>
	     </div>
	     <div>
            <label for="forRegNumber">${label.account.reg_number}</label>
            <input type="text" name="company.regNumber" 
            	placeholder="${label.account.reg_number_placeholder}" 
            	pattern="${label.account.reg_number_pattern}"
            	id="forRegNumber" required/>
	     </div>
		<div>
			<label for="forPassword">${label.account.password}</label>
			<input type="password" name="password" id="forPassword" placeholder="***********" required maxlength="50">
		</div>
		<div>
			<label for="forPasswordAgain">${label.account.repeat_password}</label>
			<input type="password" name="passwordRepeat" id="forPasswordAgain" placeholder="***********" required maxlength="50">
		</div>
	 </div>
	 <button type="submit" class="button" id="continue">${label.button.continueButton}</button>
     <p class="second-step">${label.account.secondStepReg}</p>
	</div>
	<div class="registration-second-part">
		<div class="flex-input">
			<div>
				<label for="forCountry">${label.account.city}</label>
	            <input type="text" name="company.address.city" class="city" id="forCountry" placeholder="Minsk" maxlength="100"/>
			</div>
			<div>
				<label for="forCity">${label.account.country}</label>
	            <input type="text" name="company.address.country" class="country" id="forCity" placeholder="Belarus" maxlength="50"/>
			</div>
			<div>
				<label for="forAdress">${label.account.address}</label>
	            <input type="text" name="company.address.address" class="address" id="forAdress" placeholder="Minsk" maxlength="200"/>
			</div>
			<div class="forMap google-map-container"></div>
			<div class="for-checkbox">
				<div class="checkbox-title">
					<input type="checkbox" class="" id="agree" name="" value="" />
				    <label for="agree"></label>
				    <span>${label.account.accept}<a href=""><span>${label.account.termsOfUse}</span></a></span>
				</div>
			</div>
		</div>
		<button type="button" id="go-back">${label.button.back}</button>
		<div id="form-list">
    	</div>
	</div>
	
</form>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initRegistrationMap"></script>