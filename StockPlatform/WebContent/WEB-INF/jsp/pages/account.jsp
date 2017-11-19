 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form action="/account/Save.spr" method="post">
		<input type="hidden" name="status" value="${company.status}">
		<p>${lable.account.password} <input name="password" type="password"></p>
		<p>${lable.account.name} <input disabled="disabled" name="name" value="${company.name}"></p>
		<p>${lable.account.email} <input name="email" value="${company.email}"></p>
		<p>${lable.account.phone} <input name="phone" value="${company.phone}"><br>
		<p>${lable.account.reg_number} <input disabled="disabled" name="regNumber" value="${company.regNumber}"><br>
		<p>${lable.account.country} <input name="country" value="${company.country}"><br>
		<p>${lable.account.city} <input name="city" value="${company.city}"><br>
		<input type="submit" value="${lable.button.modify }">

	</form>
	
	<ul>
        <li><a href="/lot/MyList.spr">${lable.account.my_lots}</a></li>
        <li><a href="/tender/MyList.spr">${lable.account.my_tenders}</a></li>
	</ul>
