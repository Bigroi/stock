<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${page_title}</title>
		<link rel="stylesheet" href="/css/tableStyle.css">
        <link rel="stylesheet" href="/css/menuStyle.css">
        <link rel="stylesheet" media="screen" href="/css/formStyle.css" >
		<link rel="stylesheet" media="screen" href="/css/dialogboxStyle.css" >
		<script src="/js/jQuery.js"></script> 
		<script src="/js/tableMaker.js"></script>	
		<script src="/js/tableSorter.js"></script>	
		<script src="/js/map.js"></script>
		<script src="/js/dialogbox.js"></script>
		<script src="/js/form.js"></script>
		<script src="/js/localization.js"></script>
	</head>
	<body>
		<tiles:insertAttribute name="l10n" />
		<div class="wrapper">
			<sec:authorize access="hasAnyRole('ADMIN', 'USER')">
				<div class="welcome">
					${label.navigation.welcome} ${user.username} 
					(<a href="/account/Form.spr">${label.navigation.account}</a>, 
					 <a href="/account/json/Logout.spr">${label.navigation.logout}</a>
					 )
				</div>
		    	<tiles:insertAttribute name="userHeader" />
			</sec:authorize>
		
			<sec:authorize access="hasAnyRole('ADMIN')">
		    	<tiles:insertAttribute name="adminHeader" />
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
				<div id="login-form-container"></div>
				<div class="welcome">
					<a href="#" id="loginLink">
						${label.navigation.login}
						<script type="text/javascript">setLoginDialogPlugin($("#loginLink"))</script>
					</a>
				</div>
				<tiles:insertAttribute name="anonimusHeader" />
			</sec:authorize>
			<div class="content">
				<tiles:insertAttribute name="body" />
			</div>
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>