<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<nav class="main-menu">
	<a href="/product/admin/List.spr" class='<tiles:getAsString name="nav-productsExt"/>'>
		${lable.navigation.productsExt}
	</a>
	<a href="/company/admin/List.spr" class='<tiles:getAsString name="nav-company"/>'>
		${lable.navigation.company}</a>
	<a href="/test/background/Index.spr" class='<tiles:getAsString name="nav-testBG"/>'>
		${lable.navigation.testBG}
	</a>
</nav>