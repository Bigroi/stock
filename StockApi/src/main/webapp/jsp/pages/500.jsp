<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
	
		<title>${label.index.index_title}</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="initial-scale=1.0, width=device-width">
		<meta name="context-root" content="${pageContext.request.contextPath}">
		<meta name="google-site-verification" content="57vTO4c-SbhNNvbqR0xSlTjHLgy0kd3Bi7173PGTMbc" />
		<meta name="description" content="${label.index.index_description}">
		
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Static/img/logo.png" type="image/png">
		
		<!-- application css & js -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/style.css?version=${label.build.number}">

	</head>
	<body>
		<div class="message-page-flex flex-500">
			<div>
				<h1>${label.index.error_500}</h1>
				<h3>${label.index.contact_support}</h3>
				<p>${label.index.by_phone} <a href="tel:+375292028766">+375 (29) 202-87-66</a> ${label.index.or_email} <a href="mailto:stock@info.com">stock@info.com</a></p>
			</div>
		</div>
	</body>
</html>