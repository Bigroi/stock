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
		<script src="/js/jQuery.js"></script> 
		<script src="/js/tableMaker.js"></script>	
		<script src="/js/tableSorter.js"></script>	
		<script src="/js/map.js"></script>	
	</head>
	<body>
		<sec:authorize access="hasAnyRole('ADMIN', 'USER')">
	    	<tiles:insertAttribute name="userHeader" />
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ADMIN')">
	    	<tiles:insertAttribute name="adminHeader" />
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')">
			<tiles:insertAttribute name="anonimusHeader" />
		</sec:authorize>
        
		<tiles:insertAttribute name="body" />
		<tiles:insertAttribute name="footer" />
	</body>
</html>