<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="address-form-container"></div>
<div id="table-container">
	<table id="main-table"></table>
</div>

<script>
	makeTable("/account/json/AddressesList.spr", $("#main-table"));
</script>

