<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="#" method="post" name="form" id="feedbackDeal-form">

	<input type="hidden" name="id">
	<input type="hidden" name="dealId">
	
	<h3>${label.deal.feedback}</h3>
	<div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="flex-input">
		<div>
			<label for="forMark">${label.comment.mark}</label> 
			<input type="number" name="mark" class="mark" id="forMark" required maxlength="5">
		</div>
		<div>
			<label for="forComment">${label.comment.feedback}</label>
			<textarea name="comment" class="comment" id="forComment"></textarea>
		</div>
		
		<div id="form-list"></div>
	</div>
</form>
