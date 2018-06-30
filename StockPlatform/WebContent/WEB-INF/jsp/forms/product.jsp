<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form">
	<input type="hidden" name="id" value="${product.id}">
	<input type="hidden" name="archive" required/>
	<h3>${label.product.productForm}</h3>
    <div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="flex-input">
		<div>
            <label for="name">${label.product.name}</label>
            <input type="text" name="name" required maxlength="50"/>
        </div>
        <div>
            <label for="name">${label.product.delivary_price}</label>
            <input type="text" name="delivaryPrice" required maxlength="50"/>
        </div>
        <div>
            <label for=description>${label.product.description}</label>
            <textarea name="description" cols="40" rows="6" required maxlength="500"></textarea>
        </div>
        <div id="form-list"></div>
	</div>
</form>
