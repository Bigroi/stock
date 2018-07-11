
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="#" method="post" name="form">
	<input type="hidden" name="id" value="-1"> 
	<h3>${label.lot.lotForm}</h3>
    <div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="flex-two-input">
		<div>
			<div>
				<label for="forProductId">${label.lot.product}</label> 
				<select name="productId" <c:if test="${!newLot}">disabled</c:if> id="forProductId">
					<option value="-1" selected>${label.lot.list}</option>
					<c:forEach var="product" items="${listOfProducts}">
						<option value="${product.id}">${product.name}</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<label for="forPrice">${label.lot.min_price}</label>
				<input type="text" name="price" placeholder="9.99" pattern="^\d+\.{0,1}\d{0,2}$" required id="forPrice"/>
			</div>
			<div>
				<label for="forMinVolume">${label.lot.min_volume}</label>
				<input type="text" name="minVolume" placeholder="150" pattern="^\d+$" required id="forMinVolume"/>
			</div>
			<div>
				<label for="forMaxVolume">${label.lot.max_volume}</label>
				<input type="text" name="maxVolume" placeholder="150" pattern="^\d+$" required id="forMaxVolume"/>
			</div>
			<div>
				<label for="forExpDate">${label.lot.exp_date}</label>
				<input type="text" name="exparationDate" placeholder="01.01.2018" required 
					pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])\.(0[1-9]|1[012])\.[0-9]{4}"
					id="forExpDate"/>
			</div>
		</div>
		<div>
			<div>
				<label for="forAddressId">${label.lot.address}</label>
				<select name="addressId" id="forAddressId">
					<c:forEach var="address" items="${listOfAddresses}">
						<option value="${address.id}">${address.country}, ${address.city}, ${address.address}</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<label for="forDescription">${label.lot.description}</label>
				<textarea name="description" cols="40" rows="6" maxlength="1000" id="forDescription"></textarea>
			</div>
			<div id="form-list"></div>	
		</div>
	</div>	
</form>