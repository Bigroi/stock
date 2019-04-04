<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id = "table-container">
	<input type="hidden" name="productId" value="${productId}">
    <table id = "main-table" data-url="/category/json/List?productId=${productId}"></table>
</div>