<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<nav class="main-menu">
	<a href="/Index.spr" class='<tiles:getAsString name="nav-welcome"/>'>
		${lable.navigation.welcome}
	</a>
	<a href="/lot/MyLots.spr" class='<tiles:getAsString name="nav-lots"/>'>
		${lable.navigation.lots}
	</a>
	<a href="/tender/MyTenders.spr" class='<tiles:getAsString name="nav-tenders"/>'>
		${lable.navigation.tenders}
	</a>
	<a href="/product/List.spr" class='<tiles:getAsString name="nav-products"/>'>
		${lable.navigation.products}
	</a>
	<a href="/account/Form.spr" class='<tiles:getAsString name="nav-account"/>'>
		${lable.navigation.account}
	</a>
	<a href="/access/Logout.spr">
		${lable.navigation.logout}
	</a>
</nav>