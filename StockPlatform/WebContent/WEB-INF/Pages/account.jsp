 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="AccountChangeAuth.spr" method="post">
		<p>${user.login}</p>
		<%-- <input type="hidden" name="id" value="${id}"> --%>
		 <p>password <input name="password" value="${user.password}"></p>
		 name <input name="name" value="${company.name}"><br>
		 email <input name="email" value="${company.email}"><br>
		 phone <input name="phone" value="${company.phone}"><br>
		 reg_number <input name="regNumber" value="${company.regNumber}"><br>
		 country <input name="country" value="${company.country}"><br>
		 city <input name="city" value="${company.city}"><br>
		
		<input type="submit" value="MODIFY">

	</form>
	
	<input type="button" name="back" value="BACK" onclick="document.location = 'Index.spr'">
	
	 <ul>
        <li><a href="MyLotList.spr">MyLotList</a></li>
        <li><a href="MyAplicatioList.spr">MyAplicatioList</a></li>
	</ul>
</body>
</html> 