<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>User List</h1>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/user/json/admin/List.spr", "/user/admin/ResetPassword.spr?id={id}");
		});
	</script>
</div>