<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="#" method="post" name="form">
	<ul id="form-list">
	    <li>
	        <div class="dialogbox-message"></div>
	    </li>
	    <li>
	        <label for="email">${label.account.email}:</label> 
		    <input type="email" name="email" placeholder="example@mail.com" required maxlength="50"/>
	    </li>
	    <li>
	        <label for="message">${label.account.message}:</label> 
		    <textarea name="message"></textarea>
	    </li>
	
	</ul>
</form>

