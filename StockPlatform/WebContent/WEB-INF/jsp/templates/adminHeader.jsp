<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<li class='<tiles:getAsString name="nav-productsExt"/>'>
	<a href="#" onclick="document.location = getContextRoot() + '/product/admin/List'">
		${label.navigation.productsExt}
	</a>
</li>
<li class='<tiles:getAsString name="nav-company"/>'>
	<a href="#" onclick="document.location = getContextRoot() + '/company/admin/List'">
		${label.navigation.company}
	</a>
</li>
<li class='<tiles:getAsString name="nav-testBG"/>'>
	<a href="#" onclick="document.location = getContextRoot() + '/test/background/Index'">
		${label.navigation.testBG}
	</a>
</li>
 <li class='<tiles:getAsString name="nav-label"/>'>
	<a href="#" onclick="document.location = getContextRoot() + '/label/admin/List'">
		${label.navigation.labels}
	</a>
</li> 
