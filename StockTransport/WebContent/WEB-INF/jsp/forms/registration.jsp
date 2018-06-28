<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form" id="registration-form">
	<h3>${label.registration.registration}</h3>
	<p>${label.registration.haveAcc}<span class="go_to_login" onclick="openLoginForm();"><span>${label.registration.logHere}</span></span></p>
	<div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="registration-first-part">
		<div class="flex-input">
			<div>
				<label for="forLogin">${label.registration.login}</label> <input
					type="email" name="username" id="forLogin"
					placeholder="john_doe@example.com" required maxlength="50">
			</div>
			<div>
				<label for="forPhone">${label.registration.phone}</label> 
				<input type="text" name="company.phone"
					placeholder="${label.registration.phone_placeholder}"
					pattern="${label.registration.phone_pattern}" required />
			</div>
			<div>
				<label for="forCompanyName">${label.registration.company_name}</label>
				<input type="text" name="company.name" placeholder="Stock ltd"
					id="forCompanyName" required maxlength="100" />
			</div>
			<div>
				<label for="forRegNumber">${label.registration.reg_number}</label> 
				<input type="text" name="company.regNumber"
					placeholder="${label.registration.reg_number_placeholder}"
					pattern="${label.registration.reg_number_pattern}"
					id="forRegNumber" required />
			</div>
			<div>
				<label for="forPassword">${label.registration.password}</label>
				<input type="password" name="password" id="forPassword"
					placeholder="***********" required maxlength="50">
			</div>
			<div>
				<label for="forPasswordAgain">${label.registration.repeat_password}</label>
				<input type="password" name="passwordRepeat" id="forPasswordAgain"
					placeholder="***********" required maxlength="50">
			</div>
			<div class="for-checkbox">
				<div class="checkbox-title">
					<input type="checkbox" class="" id="agree" name="" value="" /> <label
						for="agree"></label> <span>${label.registration.accept}<a
						href=""><span>${label.registration.termsOfUse}</span></a></span>
				</div>
			</div>
		</div>
		<button type="button" id="go-back">${label.button.back}</button>
		<div id="form-list"></div>
	</div>
</form>