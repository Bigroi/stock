<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<a href="/Main.spr" class='<tiles:getAsString name="nav-welcome"/>'>
	${label.navigation.welcomePage}
</a>
<a href="/product/List.spr" class='<tiles:getAsString name="nav-products"/>'>
	${label.navigation.products}
</a>
<a href="/lot/MyLots.spr" class='<tiles:getAsString name="nav-lots"/>'>
	${label.navigation.lots}
</a>
<a href="/tender/MyTenders.spr" class='<tiles:getAsString name="nav-tenders"/>'>
	${label.navigation.tenders}
</a>
<a href="/deal/MyDeals.spr" class='<tiles:getAsString name="nav-deals"/>'>
	${label.navigation.deals}
</a>
