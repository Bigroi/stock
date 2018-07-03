<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form class="form" action="#" method="post" name="form" id="account-form">
	<input type="hidden" name="company.address.latitude" class="latitude">
	<input type="hidden" name="company.address.longitude" class="longitude">
	<h3>${label.account.edit}</h3>
	<div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="flex-input">
		<div>
			<label for="forLogin">${label.account.login}</label> 
			<input type="email" name="username" id="forLogin" placeholder="john_doe@example.com" required maxlength="50">
		</div>
		<div>
			<label for="forPhone">${label.account.phone}</label> 
			<input type="text" name="company.phone" 
				placeholder="${label.account.phone_placeholder}"
				pattern="${label.account.phone_pattern}" required />
		</div>
		<div>
			<label for="forCompanyName">${label.account.company_name}</label>
			<input type="text" name="company.name" disabled="disabled" />
		</div>
		<div>
			<label for="forRegNumber">${label.account.reg_number}</label> 
			<input type="text" name="company.regNumber" disabled="disabled" />
		</div>
		<div>
			<label for="forPassword">${label.account.password}</label> 
			<input type="password" name="password" id="forPassword" placeholder="***********" maxlength="50">
		</div>
		<div>
			<label for="forPasswordAgain">${label.account.repeat_password}</label>
			<input type="password" name="passwordRepeat" id="forPasswordAgain" placeholder="***********" maxlength="50">
		</div>
		<div class="forMap google-map-container"></div>
		<div id="form-list">
			<button type="submit" class="submit button" 
				onclick="document.location = getContextRoot() + '/address/List.spr'; return false;">
				${label.button.addAddress }
			</button>
		</div>
	</div>
</form>

<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initRegistrationMap"></script>