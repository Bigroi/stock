<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="lot-form-container"></div>
<div id = "table-container">
    <table id = "main-table"></table>
</div>
<script>
	makeTable("/lot/json/MyList.spr", $("#main-table"));
</script>