 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="display: table; width:100%">
	<div style="display: table-cell; width: 40%" id="form-container">
		<div class="form-message"></div>
		<form class="form" action="#" method="post" name="form">
			<input type="hidden" name="latitude">
			<input type="hidden" name="longitude">
		    <ul>
		        <li>
		             <h2>${lable.account.account}</h2>
		        </li>
		        <li>
		            <label for="password">${lable.account.password}</label>
		            <input type="password" name="password" placeholder="************"/>
		        </li>
		         <li>
		            <label for="name">${lable.account.name}</label>
		            <input type="text" name="name" disabled="disabled"/>
		        </li>
		        <li>
		            <label for="phone">${lable.account.phone}</label>
		            <input type="text" name="phone" placeholder="+375290000000" pattern="^\+375[\d\- ]{5,13}$" required/>
		            <span class="form_hint">Proper format "+375290000000"</span>
		        </li>
		        <li>
		            <label for="regNumber">${lable.account.reg_number}</label>
		            <input type="text" name="regNumber" disabled="disabled"/>
		        </li>
		        <li>
		            <label for="country">${lable.account.country}</label>
		            <input type="text" name="country" placeholder="Belarus" required/>
		        </li>
		        <li>
		            <label for="city">${lable.account.city}</label>
		            <input type="text" name="city" placeholder="Minsk" required />
		        </li>
		        <li>
		            <label for="address">${lable.account.address}</label>
		            <input type="text" name="address" placeholder="Programmistov 11" required/>
		        </li>
		       
		        <li>
		        	<button class="submit" type="submit"
		        		onclick="
		        				return sendFormData($('#form-container'), 
			        				'/account/json/Save.spr', 
			        				function(answer){
			        					processRequestResult(answer, $('.form-message'));
			        				}); ">
		        		${lable.button.modify }
		        	</button>
		        </li>
		    </ul>
		</form>
	</div>
	<div style="display: table-cell; width: 5%"></div>
	<div style="width: 55%; height: 100%;position:relative; display: table-cell;">
		<div style="bottom:0;top: 0;right: 0; left: 0; position: absolute;">
			<div id="map" style="width: 100%; height: 100%"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	setFormData($("#form-container"), "/account/json/Form.spr", {}, function(){
		$.getScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap");
	})
</script>