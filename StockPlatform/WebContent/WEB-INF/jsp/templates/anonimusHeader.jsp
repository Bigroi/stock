<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="login-form-container"></div>
<nav class="main-menu">
	<a href="/Index.spr" class='<tiles:getAsString name="nav-welcome"/>'>
		${lable.navigation.welcome}
	</a>
	<a href="/product/List.spr" class='<tiles:getAsString name="nav-products"/>'>
		${lable.navigation.products}
	</a>
	<a href="/Registration.spr" class='<tiles:getAsString name="nav-regestration"/>'>
		${lable.navigation.regestration}
	</a>
	<a href="#" id="loginLink">
		${lable.navigation.login}
		<script type="text/javascript">setLoginDialogPlagin($("#loginLink"))</script>
	</a>
</nav>