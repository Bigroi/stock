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
		<div class="message-page-flex flex-404">
			<div>
				<h1>${label.index.page_404}</h1>
				<h3>${label.index.message_404}</h3>
				<p>Вернуться на <a href="/">главную</a></p>
				<div class="logo-apple"></div>
			</div>
		</div>
	</body>
</html>