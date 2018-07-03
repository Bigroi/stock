<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="table-header">
	<h1>${page_title}</h1>
	<div class="table-header-button <tiles:getAsString name='thb-cls'/>">
		<tiles:insertAttribute name="add-button"/>
		<div class="login-button-page">
	    	<div>${user.company.name}</div>
		</div>
		<div class="login-list">
			<a href="#" class="edit-account">${label.navigation.account}</a>
			<a href="/account/json/Logout.spr" id="session-start">${label.navigation.logout}</a>
		</div>
	</div>
</div>