<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<li class='<tiles:getAsString name="nav-products"/>'>
	<a href="#" onclick="document.location = getContextRoot() + '/product/List'" >
		${label.navigation.products}
	</a>
</li>
<li class='<tiles:getAsString name="nav-lots"/>'>
	<a href="#" onclick="document.location = getContextRoot() + '/lot/MyLots'">
		${label.navigation.lots}
	</a>
</li>
<li class='<tiles:getAsString name="nav-tenders"/>'>
	<a href="#" onclick="document.location = getContextRoot() + '/tender/MyTenders'" >
		${label.navigation.tenders}
	</a>
</li>
<li class='<tiles:getAsString name="nav-deals"/>'>
	<a href="#" onclick="document.location = getContextRoot() + '/deal/MyDeals'">
		${label.navigation.deals}
	</a>
</li>




