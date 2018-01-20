<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<nav class="main-menu">
	<a href="/product/admin/List.spr" class='<tiles:getAsString name="nav-productsExt"/>'>
		${label.navigation.productsExt}
	</a>
	<a href="/company/admin/List.spr" class='<tiles:getAsString name="nav-company"/>'>
		${label.navigation.company}</a>
	<a href="/test/background/Index.spr" class='<tiles:getAsString name="nav-testBG"/>'>
		${label.navigation.testBG}
	</a>
</nav>