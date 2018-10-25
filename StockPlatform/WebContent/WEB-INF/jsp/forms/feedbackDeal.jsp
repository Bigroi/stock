<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="#" method="post" name="form" id="feedbackDeal-form">

	<input type="hidden" name="id">
	<input type="hidden" name="dealId">
	<input type="hidden" name="mark" id="mark" value="0">
	
	<h3>${label.deal.feedback}</h3>
	<div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="flex-input">
		
		<div class="reviewStarsFeed">
		    <input id="star-5" type="radio" name="star-feedback" data-mark="5" onchange="$('#mark').val($(this).attr('data-mark'));">
		    <label title="gorgeous" for="star-5"></label>
	
		    <input id="star-4" type="radio" name="star-feedback" data-mark="4" onchange="$('#mark').val($(this).attr('data-mark'));">
		    <label title="good" for="star-4"></label>
		
		    <input id="star-3" type="radio" name="star-feedback" data-mark="3" onchange="$('#mark').val($(this).attr('data-mark'));">
		    <label title="regular" for="star-3"></label>
		
		    <input id="star-2" type="radio" name="star-feedback" data-mark="2" onchange="$('#mark').val($(this).attr('data-mark'));">
		    <label title="poor" for="star-2"></label>
		
		    <input id="star-1" type="radio" name="star-feedback" data-mark="1" onchange="$('#mark').val($(this).attr('data-mark'));">
		    <label title="bad" for="star-1"></label>
		</div>
		<div>
			<label for="forComment">${label.comment.feedback}</label>
			<textarea name="comment" class="comment" id="forComment"></textarea>
		</div>
		
		<div id="form-list"></div>
	</div>
</form>
