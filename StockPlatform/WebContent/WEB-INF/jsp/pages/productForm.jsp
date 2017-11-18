
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form action="/product/admin/Save.spr">

	<input type="hidden" name="id" value="${product.id}"> <br>
	
	name <input name="name" value="${product.name}"><br>
	
	description <input name="description" value="${product.description}"><br>
	

	<input type="submit" name="save" value="SAVE"><br> 
	<input type="button" name="back" value="Welcome page" onclick="document.location = '/admin/Index.spr'">
</form>
<c:if test="${product.id != -1}">
	<form action="/product/admin/Delete.spr">
		<input type="hidden" name="id" value="${product.id}"> <br>
		<input type="submit" value="Delete">
	</form>
</c:if>
<form action="/product/admin/List.spr">
	<input type="submit" value="List of products">
</form>