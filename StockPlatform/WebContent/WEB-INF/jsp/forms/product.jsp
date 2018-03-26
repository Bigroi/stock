
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form">
	<input type="hidden" name="id" value="${product.id}">
    <ul id="form-list">
        <li>
             <h2>${label.product.productForm }</h2>
        </li>
        <li>
	        <div class="dialogbox-message"></div>
	    </li>
        <li>
            <label for="name">${label.product.name}</label>
            <input type="text" name="name" required maxlength="50"/>
            
        </li>
        <li>
            <label for=description>${label.product.description}</label>
            <textarea name="description" cols="40" rows="6" required maxlength="500"></textarea>
        </li>
         <li>
            <input type="hidden" name="archive" required/>
        </li>
    </ul>
</form>
