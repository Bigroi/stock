<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div id="product-form-container"></div>
<div id = "table-container">
    <table id = "main-table"></table>
</div>
<script>
	makeTable("/product/json/admin/List.spr", $("#main-table"));
</script>
