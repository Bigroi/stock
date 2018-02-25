<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="Join.spr" method="get" name="form">
	<ul id="form-list">

		<li>
			<p>${label.account.login}:${userName}</p>
			<p>${label.account.password}:${userPassw}</p>
		</li>

	</ul>
</form>

