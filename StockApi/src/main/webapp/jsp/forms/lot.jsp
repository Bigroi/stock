
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form class="form" action="#" method="post" name="form">
	<input type="hidden" name="companyAddress.latitude" class="latitude">
	<input type="hidden" name="companyAddress.longitude" class="longitude">
	<input type="hidden" name="id" value="-1"> 
	<input type="hidden" name="categoryId" value="">
	<h3>${label.lot.lotForm}</h3>
    <div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="flex-two-input">
		<div>
			<div>
				<label for="forProductId">${label.lot.product} *</label> 
				<select name="productId" <c:if test="${!newLot}">disabled</c:if> id="forProductId" 
						onchange="loadCategories(this.options[this.selectedIndex].value)">
					<option value="-1" selected>${label.lot.list}</option>
					<c:forEach var="product" items="${listOfProducts}">
						<option value="${product.id}">${product.name}</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<label for="forCategoryId">${label.lot.category} *</label> 
				<select id="category-select" name="categoryId" id="forCategoryId">
					<option value="-1" selected>${label.lot.list}</option>
				</select>
			</div>
			<div>
				<label for="forPrice">${label.lot.min_price} *</label>
				<input type="number" name="price" placeholder="9.99" step="0.01" pattern="^\d+\.{0,1}\d{0,2}$" required id="forPrice"/>
			</div>
			<div>
				<label for="forMinVolume">${label.lot.min_volume} *</label>
				<input type="number" name="minVolume" placeholder="150" pattern="^\d+$" required id="forMinVolume"/>
			</div>
			<div>
				<label for="forMaxVolume">${label.lot.max_volume} *</label>
				<input type="number" name="maxVolume" placeholder="150" pattern="^\d+$" required id="forMaxVolume"/>
			</div>
			<div>
				<label for="forExpDate">${label.lot.exp_date} *</label>
				<input type="text" name="exparationDate" placeholder="01.01.2018" required 
					pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])\.(0[1-9]|1[012])\.[0-9]{4}"
					id="forExpDate"/>
			</div>
		</div>
		<div>
			<div>
				<div>
					<div>
						<label for="forAddressId">${label.lot.address} *</label>
						<select name="addressId" id="forAddressId" class="address-selector">
							<c:forEach var="address" items="${listOfAddresses}">
								<option value="${address.id}">${address.country}, ${address.city}, ${address.address}</option>
							</c:forEach>
						</select>
					</div>
					<div>
						<label for="forDistance">${label.lot.distance} *</label>
						<select name="distance" id="forDistance" class="address-selector">
							<option value="30000">${label.lot.distance_any}</option>
							<option value="400">${label.lot.distance_400}</option>
							<option value="200">${label.lot.distance_200}</option>
							<option value="100">${label.lot.distance_100}</option>
							<option value="50">${label.lot.distance_40}</option>
							<option value="0">${label.distance.distance_0}</option>
						</select>
					</div>
					<div>
						<c:if test="${foto}"><a href="${pageContext.request.contextPath}/lot/json/Foto?id=${id}" target="_blank">
							${label.lot.uploaded_foto}
						</a></c:if>
						<label for="forFoto">${label.lot.foto}</label>
						<input type="file" name="foto" id="forFoto">
					</div>
					<div>
						<label for="forDescription">${label.lot.description}</label>
						<textarea name="description" cols="40" rows="6" maxlength="1000" id="forDescription"></textarea>
					</div>
				</div>
				<div class="forMapLotsTender google-map-container"></div>
			</div>
			<div id="form-list"></div>	
		</div>
	</div>	
</form>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initRegistrationMap"></script>