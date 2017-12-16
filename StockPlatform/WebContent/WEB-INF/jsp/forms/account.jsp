 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="display: table; width:100%">
	<div style="display: table-cell; width: 30%">
		<form class="form" action="/account/Save.spr" method="post" name="form">
			<%-- <input type="hidden" name="status" value="${company.status}"> --%>
			<input type="hidden" name="latitude" value="${company.latitude}">
			<input type="hidden" name="longitude" value="${company.longitude}" >
			
		    <ul>
		        <li>
		             <h2>Contact Us</h2>
		             <span class="required_notification">* Denotes Required Field</span>
		        </li>
		        <li>
		            <label for="password">${lable.account.password}</label>
		            <input type="password" name="password" placeholder="************"/>
		        </li>
		         <li>
		            <label for="name">${lable.account.name}</label>
		            <input type="text" name="name" disabled="disabled" value="${company.name}"/>
		        </li>
		        <li>
		            <label for="phone">${lable.account.phone}</label>
		            <input type="text" name="phone" placeholder="+375290000000" pattern="^\+375[\d\- ]{5,13}$" required value="${company.phone}"/>
		            <span class="form_hint">Proper format "+375290000000"</span>
		        </li>
		        <li>
		            <label for="regNumber">${lable.account.reg_number}</label>
		            <input type="text" name="regNumber" disabled="disabled" value="${company.regNumber}"/>
		        </li>
		        <li>
		            <label for="country">${lable.account.country}</label>
		            <input type="text" name="country" placeholder="Belarus" required value="${company.country}"/>
		        </li>
		        <li>
		            <label for="city">${lable.account.city}</label>
		            <input type="text" name="city" placeholder="Minsk" required value="${company.city}"/>
		        </li>
		        <li>
		            <label for="address">${lable.account.address}</label>
		            <input type="text" name="address" placeholder="Programmistov 11" 
		            	required value="${company.address}"/>
		        </li>
		       
		        <li>
		        	<button class="submit" type="submit">${lable.button.modify }</button>
		        </li>
		    </ul>
		</form>
	</div>
	<div style="display: table-cell; width: 10%">
		
	</div>
	<div id="map" style="display: table-cell; width: 60%; hight:100%">
		
	</div>
</div>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap"></script>