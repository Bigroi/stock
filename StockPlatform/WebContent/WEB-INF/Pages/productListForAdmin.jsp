<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Product list for Admin</title>
	<link rel="stylesheet" href="/css/tableStyle.css">
	<script src="https://code.jquery.com/jquery-3.1.1.js"></script> 
	<script src="/js/tableMaker.js"></script>	
	<script src="/js/tableSorter.js"></script>
	<script>
		function noDelete() {
			alert("Sorry, but it can't be temporarily deleted!");
			return false;
		}
	</script>
</head>
<body>
	<p style="color: red;"> Все продукты видит только АДМИН (log:Admin)!!!</p>
	<h1>Product list:</h1>
	<div id = "tableContainer">	
		<script>
			$(function(){
				$("#tableContainer").tableMaker("/product/json/admin/List.spr", "json/product/form.spr?id={id}");
				/*Параметрами в плагин tableMaker идут url'ы ........ и .........*/
			});
		</script>
	</div>
	<form action="/product/admin/Form.spr" method="post">
		<input type="submit" value="Add">
	</form>
	<br>
	<form action="/admin/Index.spr" method="get">
		<input type="submit" value="Back">
	</form>
</body>
</html>