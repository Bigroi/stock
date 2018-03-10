<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="#" method="post" name="form">
	<ul id="form-list">
	
	    <li>
	         <label for="email">${label.invite.sendInvite}:</label> 
		    <input type="email" name="inviteEmail" placeholder="example@mail.com" required />
	    </li>
	
	</ul>
</form>

