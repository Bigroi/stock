<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form">
	<h3>${label.account.send_Message}</h3>
	<div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="flex-input">
		<div>
	        <label for="email">${label.account.email}:</label> 
		    <input type="email" name="email" placeholder="example@mail.com" required maxlength="50"/>
	    </div>
	    <div>
	        <label for="message">${label.account.message}:</label> 
		    <textarea name="message"></textarea>
	    </div>
	    <div id="form-list"></div>
	</div>
</form>

