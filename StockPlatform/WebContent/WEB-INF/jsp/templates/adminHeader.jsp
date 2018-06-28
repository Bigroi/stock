<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<li class='<tiles:getAsString name="nav-productsExt"/>'>
	<a href="/product/admin/List.spr">${label.navigation.productsExt}</a>
</li>
<li class='<tiles:getAsString name="nav-company"/>'>
	<a href="/company/admin/List.spr">${label.navigation.company}</a>
</li>
<li class='<tiles:getAsString name="nav-testBG"/>'>
	<a href="/test/background/Index.spr">${label.navigation.testBG}</a>
</li>
