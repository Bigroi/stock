<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:if test="${message != null}">
	<p>${message}</p>
</c:if>

<c:if test="${message  == null }">
	<p>${label.invite.errorMessage}</p>
</c:if>