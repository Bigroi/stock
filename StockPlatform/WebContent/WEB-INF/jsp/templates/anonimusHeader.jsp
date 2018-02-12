<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<a href="/Index.spr" class='<tiles:getAsString name="nav-welcome"/>'>
	${label.navigation.welcomePage}
</a>
<a href="/product/List.spr" class='<tiles:getAsString name="nav-products"/>'>
	${label.navigation.products}
</a>
