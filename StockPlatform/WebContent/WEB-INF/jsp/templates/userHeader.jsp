<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<nav class="main-menu">
	<a href="${lable.navigation.get('url.welcome')}"
	class='<tiles:getAsString name="nav-welcome"/>'>
		${lable.navigation.get('name.welcome')}
	</a>
	<a href="${lable.navigation.get('url.lots')}"
	class='<tiles:getAsString name="nav-lots"/>'>
		${lable.navigation.get('name.lots')}
	</a>
	<a href="${lable.navigation.get('url.tenders')}"
	class='<tiles:getAsString name="nav-tenders"/>'>
		${lable.navigation.get('name.tenders')}
	</a>
	<a href="${lable.navigation.get('url.products')}"
	class='<tiles:getAsString name="nav-products"/>'>
		${lable.navigation.get('name.products')}
	</a>
	<a href="${lable.navigation.get('url.account')}"
	class='<tiles:getAsString name="nav-account"/>'>
		${lable.navigation.get('name.account')}
	</a>
	<a href="${lable.navigation.get('url.logout')}">
		${lable.navigation.get('name.logout')}
	</a>
</nav>