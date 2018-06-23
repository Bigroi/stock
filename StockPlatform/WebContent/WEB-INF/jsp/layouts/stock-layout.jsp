<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <link rel="stylesheet" href="/css/pageStyle.css">
        <link rel="stylesheet" href="/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="/css/tableCustomStyle.css">
		<link rel="stylesheet" href="/css/formStyle.css">
		<link rel="stylesheet" href="/css/dialogboxStyle.css">
		<link rel="stylesheet" href="/css/buttonsStyle.css">
		<link rel="stylesheet" href="/css/jquery.responsive.dataTables.min.css"> 
        
        <script src="/js/jQuery.js"></script>
        <script src="/js/jquery.dataTables.min.js"></script>
        <script src= "/js/localization.js"></script>
		<script src= "/js/tableMaker.js"></script>
		<script src="/js/map.js"></script>
		<script src="/js/dialogbox.js"></script>
		<script src="/js/form.js"></script>
		<script src="/js/script.js"></script>
		<script src="/js/plotly-latest.min.js"></script>
		<script src="/js/jquery.responsive.dataTables.min.js"></script>
		<script src="/js/productList.js"></script>
	</head>
	<body>
		<div id="message-dialog-container"></div>
		<div id="login-form-container"></div>
        <div class = "aside">
            <div class = "logo">
                <p><b>YT</b></p>
            </div>
            <div class="burger"></div>
            <nav class = "main-menu">
			    <tiles:insertAttribute name="userHeader" />
            	<sec:authorize access="hasAnyRole('ADMIN')">
			    	<tiles:insertAttribute name="adminHeader" />
				</sec:authorize>
				<a href="#" class="contactus">${label.account.contact_us}</a>
            </nav>
            <tiles:insertAttribute name="footer" />
        </div>
        <div class="bgdark"></div>
        <div class = "section">
			<div class="login">
				<a href="/account/Form.spr">${label.navigation.account}</a>
				<a href="/account/json/Logout.spr" id="session-start">${label.navigation.logout}</a>
			</div>
            <tiles:insertAttribute name="page-title" />
            <div class="content">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
    </body>
</html>