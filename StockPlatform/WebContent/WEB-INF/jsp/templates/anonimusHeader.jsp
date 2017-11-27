<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="main-menu">
	<a href="${lable.navigation.get('url.welcome')}"
	class='<tiles:getAsString name="nav-welcome"/>'>
		${lable.navigation.get('name.welcome')}
	</a>
	<a href="${lable.navigation.get('url.products')}"
	class='<tiles:getAsString name="nav-products"/>'>
		${lable.navigation.get('name.products')}
	</a>
	<a href="${lable.navigation.get('url.regestration')}"
	class='<tiles:getAsString name="nav-regestration"/>'>
		${lable.navigation.get('name.regestration')}
	</a>
	<a href="${lable.navigation.get('url.login')}"
	class='<tiles:getAsString name="nav-login"/>'>
		${lable.navigation.get('name.login')}
	</a>
</nav>