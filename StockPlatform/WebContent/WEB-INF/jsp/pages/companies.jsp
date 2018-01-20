<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<h1>${label.companies.title}</h1>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/company/json/admin/List.spr", "/company/admin/ChangeStatus.spr?id={id}");
		});
	</script>
</div>