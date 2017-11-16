<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome page</title>
</head>
<body>
   <p> WELCOME !!! ${user.name}</p>  
   <a href="?lang=en">en</a> |  <a href="?lang=ru">ru</a>
   <c:if test="${user == null }">
   <p><a href="/Login.spr"><spring:message code="label.login"/></a> </p>
   <p><a href="/Registration.spr"><spring:message code="label.registration"/></a> </p>
   </c:if>
   <p><a href="/account/Form.spr"><spring:message code="label.account"/></a> </p>

	<input type="button" value="<spring:message code="label.logout"/>" onclick="document.location = '<c:url value="/logout" />'"><!--  /access/Logout.spr  <c:url value="/logout" /> -->
	<p style="font-family: cursive; font-size: 13px">${outMessage}</p>
	<ul>
    	<li><a href="/product/List.spr"><spring:message code="label.productList"/></a>
		</li>
        <li><a href="/lot/MyList.spr"><spring:message code="label.myLotList"/></a>
        	<form action="/lot/Form.spr">
				<input type="hidden" name="id" value="-1" />
				<p><input type="submit" value="<spring:message code="label.button.AddLot"/>"></p>	
			</form>
        </li>
        <li><a href="/tender/MyList.spr"><spring:message code="label.tenderList"/></a>
        	<form action="/tender/Form.spr">
				<input type="hidden" name="id" value="-1" />
				<p><input type="submit" value="<spring:message code="label.button.AddTender"/>"></p>
			</form>
        </li>
        <li>
        <a href="/admin/Index.spr"> Admin page</a>
       
        </li>
        
	</ul>
	
</body>
</html>