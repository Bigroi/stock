
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form">
	<input type="hidden" name="id" value="${product.id}">
    <ul id="form-list">
        <li>
             <h2>${lable.product.productForm }</h2>
        </li>
        <li>
            <label for="name">${lable.product.name}</label>
            <input type="text" name="name" required/>
            
        </li>
        <li>
            <label for=description>${lable.product.description}</label>
            <textarea name="description" cols="40" rows="6" required></textarea>
        </li>
    </ul>
</form>
