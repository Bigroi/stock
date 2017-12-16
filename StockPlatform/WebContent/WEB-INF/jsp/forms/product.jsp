
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form">
	<input type="hidden" name="id" value="${product.id}">
    <ul id="form-list">
        <li>
             <h2>Contact Us</h2>
             <span class="required_notification">* Denotes Required Field</span>
        </li>
        <li>
            <label for="name">${lable.productForm.name}</label>
            <input type="text" name="name" required value="${product.name}"/>
            
        </li>
        <li>
            <label for=description>${lable.productForm.description}</label>
            <textarea name="description" cols="40" rows="6" required>"${product.description}"</textarea>
        </li>
    </ul>
</form>
