
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="#" method="post" name="form">
	<input type="hidden" name="id" value=""> 
	
	<ul id="form-list">
		<li>
            <h2>Contact Us</h2>
            <span class="required_notification">* Denotes Required Field</span>
       	</li>
       	<li>
			<label for="productId">${lable.lotForm.product}</label>
			<select name="productId" <c:if test="${!newLot}">disabled</c:if>>
				<c:forEach var="product" items="${listOfProducts}">
					<option value="${product.id}">${product.name}</option>
				</c:forEach>
			</select>
		</li>
		
    	<li>
            <label for="description">${lable.lotForm.description}</label>
            <textarea name="description" cols="40" rows="6"></textarea>
        </li>
        <li>
            <label for="minPrice">${lable.lotForm.min_price}</label>
            <input type="text" name="minPrice" placeholder="9.99" pattern="^\d+\.{0,1}\d{0,2}$" required/>
            <span class="form_hint">Proper format "9.99"</span>
        </li>
        <li>
            <label for="minVolume">${lable.lotForm.min_volume}</label>
            <input type="text" name="minVolume" placeholder="150" pattern="^\d+$" required/>
            <span class="form_hint">Proper format "150"</span>
        </li>
          <li>
            <label for="maxVolume">${lable.lotForm.max_volume}</label>
            <input type="text" name="maxVolume" placeholder="15000" pattern="^\d+$" required/>
            <span class="form_hint">Proper format "15000"</span>
        </li>
         <li>
            <label for="expDate">${lable.lotForm.exp_date}</label>
            <input type="text" name="expDate" placeholder="01.01.2018" required  
            	pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])\.(0[1-9]|1[012])\.[0-9]{4}"/>
            <span class="form_hint">Proper format "01.01.2018"</span>
        </li>
    </ul>
</form>