
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="tender-form-container"></div>
<div id = "table-container">
    <table id = "main-table" class="display responsive nowrap" style="width:100%"></table>
</div>
<script>
	makeTable("/tender/json/MyList.spr", $("#main-table"));
</script>
