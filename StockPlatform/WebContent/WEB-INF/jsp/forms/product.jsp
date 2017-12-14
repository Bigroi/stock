
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form" action="/product/admin/Save.spr" method="post" name="form">
	<input type="hidden" name="id" value="${product.id}">
    <ul>
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
        <li>
        	<button class="submit" type="submit">${lable.button.save }</button>
        </li>
    </ul>
</form>

<c:if test="${product.id != -1}">
	<form class="form" action="/product/admin/Delete.spr" method="post">
		<input type="hidden" name="id" value="${product.id}">
		<ul>
			<li>
				<button class="submit" type="submit">${lable.button.delete}</button>
			</li>
		</ul>
	</form>
</c:if>
<form class="form" action="/product/admin/List.spr" method="post">
	
	<ul>
		<li>
			<button class="submit" type="submit">${lable.productForm.products}</button>
		</li>
	</ul>
</form>
