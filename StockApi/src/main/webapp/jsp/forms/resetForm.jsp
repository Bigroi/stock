<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<form class="form" action="#" method="post" name="form" id="login-form">
		<h3>${label.login.loginResetForm}</h3>
		<div>
			<div class="dialogbox-message"></div>
		</div>
		<div>
			<label for="forLogin">${label.login.login}</label>
			<input type="email" name="username" id="forLogin" placeholder="John Doe" required maxlength="50">
		</div>
		<div>
		    <div id="form-list">
		    </div>
	    </div>
	</form>