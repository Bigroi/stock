<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form">
	<input type="hidden" name="id">
	<input type="hidden" name="productId">
	<input type="hidden" name="archive" required/>
	<h3>${label.product_category.category_form}</h3>
    <div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="flex-input">
		<div>
            <label for="categoryName">${label.product_category.name} *</label>
            <input type="text" name="categoryName" required maxlength="500"/>
        </div>
        <div id="form-list"></div>
	</div>
</form>
