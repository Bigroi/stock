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
		<!-- <div>
			<label for="forMark">${label.comment.mark}</label> 
			<input type="number" name="mark" class="mark" id="forMark" required maxlength="5">
		</div>  -->
		<div class="reviewStarsFeed">
		    <input id="star-5" type="radio" name="reviewStars">
		    <label title="gorgeous" for="star-5"></label>
	
		    <input id="star-4" type="radio" name="reviewStars">
		    <label title="good" for="star-4"></label>
		
		    <input id="star-3" type="radio" name="reviewStars">
		    <label title="regular" for="star-3"></label>
		
		    <input id="star-2" type="radio" name="reviewStars">
		    <label title="poor" for="star-2"></label>
		
		    <input id="star-1" type="radio" name="reviewStars">
		    <label title="bad" for="star-1"></label>
		</div>
		<div>
			<label for="forComment">${label.comment.feedback}</label>
			<textarea name="comment" class="comment" id="forComment"></textarea>
		</div>
		
		<div id="form-list"></div>
	</div>
</form>
