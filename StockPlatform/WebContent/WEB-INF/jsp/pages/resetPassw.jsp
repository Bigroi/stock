<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<p>${message}</p>
<%-- <p><a href="${pageContext.request.contextPath}">Home</a></p>  --%>
<button class="submit fs-submit gray-button" type="submit"
	onclick="document.location = getContextRoot() + '/Index.spr'; return false;">${label.button.back}
</button> 
