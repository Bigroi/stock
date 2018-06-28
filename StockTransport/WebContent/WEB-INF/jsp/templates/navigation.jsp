<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<li class='<tiles:getAsString name="dealList"/>'>
	<a href="deal-list.spr">Deal List</a>
</li>
<li class='<tiles:getAsString name="propositions"/>'> 
	<a href="propositions.spr">My Propositions</a>
</li>
<li class='<tiles:getAsString name="history"/>'> 
	<a href="history.spr">History</a>
</li>