
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="tender-form-container"></div>
<h1>${lable.myDeals.title}</h1>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/deal/json/MyDeals.spr", "/deal/Form.spr?id={id}");
		});
	</script>
</div>