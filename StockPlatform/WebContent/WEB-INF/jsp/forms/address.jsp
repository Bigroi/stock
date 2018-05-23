<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<h2 style="color: red; size: 15px">the page under construction</h2>

<div style="display: table; width: 100%" id="deal-style-mob">
	<div style="display: table-cell; width: 40%" id="form-container">
		<div class="form-message"></div>
		
		<form class="form" action="#" method="post" name="form">
		
			<input type="hidden" name="company.address.latitude" class="latitude">
			<input type="hidden" name="company.address.longitude"class="longitude"> 
			<input type="hidden" name="id">

			<ul>
				<li><label for="country">${label.address.country}</label> 
				<input type="text" name="country" /></li>
			</ul>

			<ul>
				<li><label for="city">${label.address.city}</label> 
				<input type="text" name="city" /></li>
			</ul>

			<ul>
				<li><label for="address">${label.address.address}</label> 
				<input type="text" name="address" /></li>
			</ul>
			
		</form>
	</div>
	<div style="display: table-cell; width: 5%"></div>
	<div
		style="width: 55%; height: 100%; position: relative; display: table-cell;">
		<div style="bottom: 0; top: 0; right: 0; left: 0; position: absolute;"
			id="map-mob-deal">
			<div id="map" style="width: 100%; height: 100%"></div>
		</div>
	</div>
</div>
