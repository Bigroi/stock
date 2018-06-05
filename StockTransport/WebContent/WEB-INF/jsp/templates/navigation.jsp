<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<a class='<tiles:getAsString name="dealList"/>' href="deal-list.spr">Deal List</a>
<a class='<tiles:getAsString name="propositions"/>' href="propositions.spr">My Propositions</a>
<a class='<tiles:getAsString name="history"/>' href="history.spr">History</a>