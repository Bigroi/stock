
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="/tender/Save.spr" method="post" name="form">
	<input type="hidden" name="id" value="-1"> 
	
	<ul id="form-list">
		<li>
            <h2>${label.tender.tenderForm}</h2>
       	</li>
       	<li>
			<label for="productId">${label.tender.product}</label>
			<select name="productId" <c:if test="${!newTender}">disabled</c:if>>
				<option value="-1" selected>${label.tender.list}</option>
				<c:forEach var="product" items="${listOfProducts}">
					<option value="${product.id}">${product.name}</option>
				</c:forEach>
			</select>                         	
		</li>
		
    	<li>
            <label for="description">${label.tender.description}</label>
            <textarea name="description" cols="40" rows="6"></textarea>
        </li>
        <li>
            <label for="maxPrice">${label.tender.max_price}</label>
            <input type="text" name="maxPrice" placeholder="9.99" pattern="^\d+\.{0,1}\d{0,2}$" required />
            <span class="form_hint">Proper format "9.99"</span>
        </li>
        <li>
            <label for="minVolume"> ${label.tender.min_volume }</label>
            <input type="text" name="minVolume" placeholder="150" required pattern="^\d+$"/>
            <span class="form_hint">Proper format "150"</span>
        </li>
          <li>
            <label for="maxVolume"> ${label.tender.max_volume}</label>
            <input type="text" name="maxVolume" placeholder="15000" required pattern="^\d+$"/>
            <span class="form_hint">Proper format "15000"</span>
        </li>
         <li>
            <label for="expDate">${label.tender.exp_date}</label>
            <input type="text" name="expDate" placeholder="01.01.2018" required   
            	pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])\.(0[1-9]|1[012])\.[0-9]{4}"/>
            <span class="form_hint">Proper format "01.01.2018"</span>
        </li>
        <li>
		   <label for="delivery">${label.tender.delivery}</label> 
		   <select name="delivery">
				<option value="0">SELF</option>
				<option value="1">NO</option>
		   </select>
		</li>
		<li>
		   <label for="packaging">${label.tender.packaging}</label> 
		   <select name="packaging">
				<option value="0">YES</option>
				<option value="1">NO</option>
		   </select>
		</li>
    </ul>
</form>