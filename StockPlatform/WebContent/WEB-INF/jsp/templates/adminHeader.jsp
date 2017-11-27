<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<nav class="main-menu">
	<a 
	href="${lable.navigation.get('url.users')}"
	class='<tiles:getAsString name="nav-users"/>'>
		${lable.navigation.get('name.users')}
	</a>
	<a 
	href="${lable.navigation.get('url.productsExt')}"
	class='<tiles:getAsString name="nav-productsExt"/>'>
		${lable.navigation.get('name.productsExt')}
	</a>
	<a 
	href="${lable.navigation.get('url.company')}"
	class='<tiles:getAsString name="nav-company"/>'>
		${lable.navigation.get('name.company')}</a>
	<a 
	href="${lable.navigation.get('url.testBG')}"
	class='<tiles:getAsString name="nav-testBG"/>'
	>
		${lable.navigation.get('name.testBG')}
	</a>
</nav>