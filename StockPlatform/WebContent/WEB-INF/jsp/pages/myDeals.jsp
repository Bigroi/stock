<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id = "table-container">
    <table id = "main-table"></table>
</div>
<script>
	makeTable("/deal/json/MyDeals.spr", $("#main-table"));
</script>