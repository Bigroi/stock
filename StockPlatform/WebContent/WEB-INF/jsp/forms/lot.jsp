
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="/lot/Save.spr" method="post" name="form">
	<input type="hidden" name="id" value="${lot.id}"> 
	
	<ul>
		<li>
            <h2>Contact Us</h2>
            <span class="required_notification">* Denotes Required Field</span>
       	</li>
       	<li>
			<label for="productId">${lable.lotForm.product}</label>
			<c:forEach var="product" items="${listOfProducts}">
				<c:if test="${lot.productId == product.id}">
			    	<input type="text" disabled value="${product.name}"/>
			    </c:if>
			</c:forEach>
		
			<c:if test="${lot.id == '-1'}">
				<select name="productId">
					<c:forEach var="product" items="${listOfProducts}">
						<option value="${product.id}">${product.name}</option>
					</c:forEach>
				</select>                         	
			</c:if>
		</li>
		
    	<li>
            <label for="description">${lable.lotForm.description}</label>
            <textarea name="description" cols="40" rows="6">${lot.description}</textarea>
        </li>
        <li>
            <label for="status">${lable.lotForm.status}</label>
            <input type="text" name="status" disabled value="${lot.status}"/>
        </li>
        <li>
            <label for="minPrice">${lable.lotForm.min_price}</label>
            <input type="number" name="minPrice" placeholder="9.99" required value="${lot.minPrice}"/>
            <span class="form_hint">Proper format "9.99"</span>
        </li>
        <li>
            <label for="minVolume">${lable.lotForm.min_volume}</label>
            <input type="number" name="minVolume" placeholder="150" required value="${lot.minVolume }"/>
            <span class="form_hint">Proper format "150"</span>
        </li>
          <li>
            <label for="volume">${lable.lotForm.max_volume}</label>
            <input type="number" name="volume" placeholder="15000" required value="${lot.volume }"/>
            <span class="form_hint">Proper format "15000"</span>
        </li>
         <li>
            <label for="expDate">${lable.lotForm.exp_date}</label>
            <input type="text" name="expDate" placeholder="01.01.2018" required value="${lot.dateStr}"  
            	pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])\.(0[1-9]|1[012])\.[0-9]{4}"/>
            <span class="form_hint">Proper format "01.01.2018"</span>
        </li>
        <li>
        	<button class="submit" type="submit">${lable.button.save}</button>
        </li>
    </ul>
</form>

<c:if test="${lot.id ne '-1'}">	
	<form action="/lot/StartTrading.spr" class="form" method="post">
		<input type="hidden" name="id" value="${lot.id}">
		<button class="submit" type="submit">${lable.lotForm.start_trading}</button>
	</form>
		
	<form action="/lot/Cancel.spr" class="form" method="post">
		<input type="hidden" name="id" value="${lot.id}">
		<button class="submit" type="submit">${lable.lotForm.cancel}</button>
	</form>
</c:if>

<form action="/lot/MyLots.spr" class="form">
	<button class="submit" type="submit">${lable.lotForm.my_lots}</button>
</form>
