<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<form class="form" action="#" method="post" name="form" id="login-form">
		<h3>${label.login.loginForm}</h3>
		<p>${label.login.dontHaveAcc}<span class="go_to_registration" onclick="openRegistrationForm();"><span>${label.login.registerHere}</span></span></p>
		<div>
			<div class="dialogbox-message"></div>
		</div>
		<div>
			<label for="forLogin">${label.login.login}</label>
			<input type="email" name="username" id="forLogin" placeholder="John Doe" required maxlength="50">
			<!--<span class="form_hint">Proper format "name@something.com"</span>  -->
		</div>
		<div>
			<label for="forPassword">${label.login.password}</label>
			<input type="password" name="password" id="forPassword" placeholder="***********" required maxlength="50">
			<a id="reset" href="" onclick="return sendResetFormData()"><span>${label.login.forgot}</span></a>
		</div>
		<div>
		    <div id="form-list">
		    </div>
	    </div>
	</form>