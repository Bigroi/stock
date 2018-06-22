<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>proposition list:</h2>

<table border="3">
	<thead>
		<tr>
			<td>dealId</td>
			<td>companyId</td>
			<td>price</td>
			<td>status</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="prop" items="${listOfPropositions}">
			<tr>
				<td>${prop.dealId}</td>
				<td>${prop.companyId}</td>
				<td>${prop.price}</td>
				<td>${prop.status }</td>
			</tr>
		</c:forEach> 
	</tbody>
</table>