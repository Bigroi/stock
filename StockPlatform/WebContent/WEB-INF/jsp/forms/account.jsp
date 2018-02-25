 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="display: table; width:100%" id="table-style-mob">
	<div style="display: table-cell; width: 40%" id="form-container">
		<div class="form-message"></div>
		<form class="form" action="#" method="post" name="form">
			<input type="hidden" name="latitude">
			<input type="hidden" name="longitude">
		    <ul>
		        <li>
		        </li>
		        <li>
		            <label for="password">${label.account.password}</label>
		            <input type="password" name="password" placeholder="************"/>
		        </li>
		         <li>
		            <label for="name">${label.account.name}</label>
		            <input type="text" name="name" disabled="disabled"/>
		        </li>
		        <li>
		            <label for="phone">${label.account.phone}</label>
		            <input type="text" name="phone" placeholder="+375290000000" pattern="^\+375[\d\- ]{5,13}$" required/>
		            <span class="form_hint">Proper format "+375290000000"</span>
		        </li>
		        <li>
		            <label for="regNumber">${label.account.reg_number}</label>
		            <input type="text" name="regNumber" disabled="disabled"/>
		        </li>
		        <li>
		            <label for="country">${label.account.country}</label>
		            <input type="text" name="country" placeholder="Belarus" required/>
		        </li>
		        <li>
		            <label for="city">${label.account.city}</label>
		            <input type="text" name="city" placeholder="Minsk" required />
		        </li>
		        <li>
		            <label for="address">${label.account.address}</label>
		            <input type="text" name="address" placeholder="Programmistov 11" required/>
		        </li>
		       
		        <li>
		        	<button class="submit but-sub" type="submit"
		        		onclick="
		        				return sendFormData($('#form-container > form'), 
			        				function(formContainer, param){
		        						$.post('/account/json/Save.spr', param, function(answer){
		        							processRequestResult($('#form-container > form'), answer, $('.form-message'));
		        						});
		        					}); ">
		        		${label.button.modify }
		        	</button>
		        </li>
		    </ul>
		</form>
		
			<form class="form" action="#" method="post" name="form">
				
				 <button class="submit" id="new-invite-button">${label.button.invite}</button>
				<script type="text/javascript">setInviteDialogPlugin($("#new-invite-button"))</script> 

		    </form>
		
	</div>
	<div style="display: table-cell; width: 5%"></div>
	<div style="width: 55%; height: 100%;position:relative; top: 20px; display: table-cell;" id="map-mob-acc">
		<div style="bottom:0;top: 0;right: 0; left: 0; position: absolute;">
			<div id="map" style="width: 100%; height: 100%"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	setFormData($("#form-container > form"), "/account/json/Form.spr", {}, function(){
		$.getScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initMap");
	})
</script>