
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="/css/tableStyle.css">
	<script src="https://code.jquery.com/jquery-3.1.1.js"></script> 
	<script src="/js/tableMaker.js"></script>	
	<script src="/js/tableSorter.js"></script>
</head>
<body>
	<h1>My tenders</h1>
	<div id = "tableContainer">	
		<script>
			$(function(){
				$("#tableContainer").tableMaker("testJson/table.json", "json/product/form.spr?id={id}");
				/*Параметрами в плагин tableMaker идут url'ы ........ и .........*/
			});
		</script>
	</div>
	<form action="/tender/Form.spr">
		<input type="submit" value="Add tender">
	</form>
	<form action="/Index.spr">
		<input type="submit" value="Welcome page">
	</form>
</body>
</html>