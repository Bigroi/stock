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
   <p> WELCOME !!! ${user.login}</p>  
   <a href="?lang=en">en</a> |  <a href="?lang=ru">ru</a>
   <c:if test="${user.login == null }">
   <p><a href="LoginPage.spr"><spring:message code="label.login"/></a> </p>
   <p><a href="RegistrationPage.spr"><spring:message code="label.registration"/></a> </p>
   </c:if>
   <p><a href="AccounPageAuth.spr"><spring:message code="label.account"/></a> </p>

	<input type="button" value="<spring:message code="label.logout"/>" onclick="document.location = 'Logout.spr'">
	<p style="font-family: cursive; font-size: 13px">${outMessage}</p>

	<ul>
    	<li><a href="ProductListPage.spr"><spring:message code="label.productList"/></a>
    		<form action="ProductForm.spr">
				<input type="hidden" name="id" value="-1" />
				<p><input type="submit" value="<spring:message code="label.button.AddProduct"/>"></p>
			</form>
		</li>
        <li><a href="MyLotListAuth.spr"><spring:message code="label.myLotList"/></a>
        	<form action="LotFormAuth.spr">
				<input type="hidden" name="id" value="-1" />
				<p><input type="submit" value="<spring:message code="label.button.AddLot"/>"></p>	
			</form>
        </li>
        <li><a href="MyTenderListAuth.spr"><spring:message code="label.tenderList"/></a>
        	<form action="TenderFormAuth.spr">
				<input type="hidden" name="id" value="-1" />
				<p><input type="submit" value="<spring:message code="label.button.AddTender"/>"></p>
			</form>
        </li>
        <li>
        <a href="CompanyList.spr"> Company list with status</a>
       
        </li>
        
	</ul>
	
	<a href="ProductListAdmin.spr" > Product list for admin's panel</a>
	

</body>
</html>