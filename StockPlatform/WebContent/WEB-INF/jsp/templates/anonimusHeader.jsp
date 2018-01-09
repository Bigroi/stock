<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="main-menu">
	<a href="/Index.spr" class='<tiles:getAsString name="nav-welcome"/>'>
		${lable.navigation.welcomePage}
	</a>
	<a href="/product/List.spr" class='<tiles:getAsString name="nav-products"/>'>
		${lable.navigation.products}
	</a>
	<a href="/account/Registration.spr" class='<tiles:getAsString name="nav-regestration"/>'>
		${lable.navigation.regestration}
	</a>
</nav>