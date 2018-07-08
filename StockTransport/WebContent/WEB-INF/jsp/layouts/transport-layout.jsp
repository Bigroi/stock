<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- Global site tag (gtag.js) - Google Analytics -->
		<script async src="https://www.googletagmanager.com/gtag/js?id=UA-114993452-1"></script>
		<script>
		  window.dataLayer = window.dataLayer || [];
		  function gtag(){dataLayer.push(arguments);}
		  gtag('js', new Date());
		
		  gtag('config', 'UA-114993452-1');
		</script>
		
		<title>Your Trader! ${page_title}</title>
		<meta charset = "utf-8">
		<meta name="viewport" content="initial-scale=1.0, width=device-width">
		<meta name="context-root" content="${pageContext.request.contextPath}">
        <c:choose>
			<c:when test="${devProperties.development}">
		        <link rel="stylesheet" href="/Static/css/jquery.dataTables.min.css">
		        <link rel="stylesheet" href="/Static/css/jquery.responsive.dataTables.min.css"> 
		        <link rel="stylesheet" href="/Static/css/style.css">
		        
		        <script src="/Static/js/jQuery.js"></script>
		        <script src="/Static/js/jquery.dataTables.min.js"></script>
		        <script src="/Static/js/jquery.responsive.dataTables.min.js"></script>
		        <script src="/Static/js/form.js"></script>
		        <script src="/Static/js/intiFormParams.js"></script>
		        <script src="/Static/js/localization.js"></script>
				<script src="/Static/js/tableMaker.js"></script>
				<script src="/Static/js/map.js"></script>
				<script src="/Static/js/dialogbox.js"></script>
				<script src="/Static/js/script.js"></script>
				<script src="/Static/js/productList.js"></script>
			</c:when>
			<c:otherwise>
				<link rel="stylesheet" href="/Static/css/jquery.dataTables.min${label.build.number}.css">
				<link rel="stylesheet" href="/Static/css/jquery.responsive.dataTables.min${label.build.number}.css"> 
		        <link rel="stylesheet" href="/Static/css/style${label.build.number}.css">
				
		        <script src="/Static/js/jQuery${label.build.number}.js"></script>
		        <script src="/Static/js/jquery.dataTables.min${label.build.number}.js"></script>
		        <script src="/Static/js/jquery.responsive.dataTables.min${label.build.number}.js"></script>
		        <script src="/Static/js/form${label.build.number}.js"></script>
		        <script src="/Static/js/intiFormParams${label.build.number}.js"></script>
		        <script src="/Static/js/localization${label.build.number}.js"></script>
				<script src="/Static/js/tableMaker${label.build.number}.js"></script>
				<script src="/Static/js/map${label.build.number}.js"></script>
				<script src="/Static/js/dialogbox${label.build.number}.js"></script>
				<script src="/Static/js/script${label.build.number}.js"></script>
				<script src="/Static/js/productList${label.build.number}.js"></script>
			</c:otherwise>
		</c:choose>
	</head>
	<body class="body-pages">
		<div id="message-dialog-container"></div>
		<div id="form-container"></div>
        <div class = "aside">
            <div class="logo-pages">
                <img src="/Static/img/logo-pages.png" alt="YourTrader" title="YourTrader">
            </div>
            <div class="burger"></div>
            <nav class="main-menu">
            	<ul>
	            	<tiles:insertAttribute name="navigation" />
            	</ul>
            </nav>
            <tiles:insertAttribute name="footer" />
        </div>
        <div class="bgdark"></div>
        <div class = "section">
            <tiles:insertAttribute name="page-title" />
            <div class="content">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
    </body>
</html>