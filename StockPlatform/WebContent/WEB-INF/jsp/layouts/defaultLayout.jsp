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
	</head>
	<body>
		<div class="wrapper">
			<sec:authorize access="hasAnyRole('ADMIN', 'USER')">
		    	<tiles:insertAttribute name="userHeader" />
			</sec:authorize>
		
			<sec:authorize access="hasAnyRole('ADMIN')">
		    	<tiles:insertAttribute name="adminHeader" />
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
				<tiles:insertAttribute name="anonimusHeader" />
			</sec:authorize>
			<div class="content">
				<tiles:insertAttribute name="body" />
			</div>
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>