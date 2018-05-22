 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <div id = "table-container">
    <table id = "main-table"></table>
</div>
<h2 style="color: red; size: 15px">the page under construction</h2>
<script>
	makeTable("/account/json/AddressesList.spr", $("#main-table"));
</script>
<!-- <script type="text/javascript">
	setFormData($("#form-container > form"), "/account/json/Form.spr", {}, function(){
		$.getScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap");
	})
</script> -->
