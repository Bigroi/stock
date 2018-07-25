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
		<meta name="context-root" content="${pageContext.request.contextPath}">
		<meta name="viewport" content="initial-scale=1.0, width=device-width">
		
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Static/img/logo.png" type="image/png">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/jquery.responsive.dataTables.min.css"> 
		
		<script src="${pageContext.request.contextPath}/Static/js/jQuery.js"></script>
        <script src="${pageContext.request.contextPath}/Static/js/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/Static/js/jquery.responsive.dataTables.min.js"></script>
		
		<!-- application css & js -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/style.css?version=${label.build.number}">
        
        <script src="${pageContext.request.contextPath}/Static/js/form.js?version=${label.build.number}"></script>
        <script src="${pageContext.request.contextPath}/Static/js/intiFormParams.js?version=${label.build.number}"></script>
        <script src="${pageContext.request.contextPath}/Static/js/localization.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/tableMaker.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/map.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/dialogbox.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/script.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/productList.js?version=${label.build.number}"></script>
			
	</head>
	<body class="body-pages">
		<div id="message-dialog-container"></div>
		<div id="form-container"></div>
        <div class="aside">
            <div class="logo-pages">
                <img class="big-logo" src="/Static/img/logo-pages.png" alt="YourTrader" title="YourTrader">
                <div class=" small-logo">
                	<div class="burger-close"></div>
                	<div class="logo-mobile"></div>
                </div>
            </div>
            <nav class="main-menu">
            	<ul>
            		<c:if test="${not empty user}">
		            	<tiles:insertAttribute name="userHeader" />
		            	<sec:authorize access="hasAnyRole('ADMIN')">
					    	<tiles:insertAttribute name="adminHeader" />
						</sec:authorize>
					</c:if>
            	</ul>
            </nav>
            <tiles:insertAttribute name="footer" />
        </div>
       	<div class="aside-modile">
         	 <div class="burger-logo">
         	 	<div class="burger"></div>
         	 	<div class="logo-mobile"></div>
 			</div>
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