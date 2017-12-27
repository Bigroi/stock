<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<h1>${lable.companies.title}</h1>
<div id = "lables">lable.account.name,lable.account.phone,lable.account.reg_number,lable.account.status</div>
<script>
$("#lables").localization();
</script>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/company/json/admin/List.spr", "/company/admin/ChangeStatus.spr?id={id}");
		});
	</script>
</div>