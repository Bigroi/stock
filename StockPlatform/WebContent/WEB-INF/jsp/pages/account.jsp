 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form action="/account/Save.spr" method="post">
		<p>${user.login}</p>
		<%-- <input type="hidden" name="id" value="${id}"> --%>
		<input type="hidden" name="status" value="${company.status}">
		 <p>password <input name="password" type="password"></p>
		 name <input name="name" value="${company.name}"><br>
		 email <input name="email" value="${company.email}"><br>
		 phone <input name="phone" value="${company.phone}"><br>
		 reg_number <input name="regNumber" value="${company.regNumber}"><br>
		 country <input name="country" value="${company.country}"><br>
		 city <input name="city" value="${company.city}"><br>
		 status - ${company.status}<br>		
		<input type="submit" value="MODIFY">

	</form>
	
	<input type="button" name="back" value="BACK" onclick="document.location = '/Index.spr'">
	
	 <ul>
        <li><a href="/lot/MyList.spr">My lots list</a></li>
        <li><a href="/tender/MyList.spr">My tenders list</a></li>
	</ul>
