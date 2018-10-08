<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="table-header">
	<h1>${page_title}</h1>
	<div class="table-header-button <tiles:getAsString name='thb-cls'/>">
		<tiles:insertAttribute name="add-button"/>
		<c:if test="${not empty user}">
			<div class="login-box login-box-shadow">
				<div class="login-button-page">
			    	<div>${user.company.name}</div>
				</div>
				<div class="login-list">
					<div>
						<a href="#" class="edit-account">${label.navigation.account}</a>
						<a href="#" onclick="document.location = getContextRoot() + '/account/json/Logout.spr'" id="session-start">
							${label.navigation.logout}
						</a>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</div>