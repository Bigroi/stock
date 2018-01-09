<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<nav class="main-menu">
	<a href="/Index.spr" class='<tiles:getAsString name="nav-welcome"/>'>
		${lable.navigation.welcomePage}
	</a>
	<a href="/lot/MyLots.spr" class='<tiles:getAsString name="nav-lots"/>'>
		${lable.navigation.lots}
	</a>
	<a href="/tender/MyTenders.spr" class='<tiles:getAsString name="nav-tenders"/>'>
		${lable.navigation.tenders}
	</a>
	<a href="/deal/MyDeals.spr" class='<tiles:getAsString name="nav-deals"/>'>
		${lable.navigation.deals}
	</a>
	<a href="/product/List.spr" class='<tiles:getAsString name="nav-products"/>'>
		${lable.navigation.products}
	</a>
</nav>