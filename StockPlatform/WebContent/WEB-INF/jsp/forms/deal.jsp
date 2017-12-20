 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="display: table; width:100%">
	<div style="display: table-cell; width: 30%" id="form-container">
		<div class="form-message"></div>
		<form class="form" action="#" method="post" name="form">
			<input type="hidden" name="latitude">
			<input type="hidden" name="longitude">
			<input type="hidden" name="id">
		    <ul>
		        <li>
		             <h2>Contact Us</h2>
		             <span class="required_notification">* Denotes Required Field</span>
		        </li>
		        
		        <li>
		            <label for="productName">${lable.deal.productName}</label>
		            <input type="text" name="productName" disabled/>
		        </li>
		        <li>
		            <label for="price">${lable.deal.price}</label>
		            <input type="text" name="price" disabled/>
		        </li>
		        <li>
		            <label for="volume">${lable.deal.volume}</label>
		            <input type="text" name="volume" disabled/>
		        </li>
		        <li>
		            <label for="time">${lable.deal.time}</label>
		            <input type="text" name="time" disabled/>
		        </li>
		        <li>
		            <label for="partnerName">${lable.deal.partnerName}</label>
		            <input type="text" name="partnerName" disabled/>
		        </li>
		        <li>
		            <label for="partnerAddress">${lable.deal.partnerAddress}</label>
		            <textarea name="partnerAddress" disabled></textarea>
		        </li>
		        <li>
		            <label for="partnerComment">${lable.deal.partnerComment}</label>
		            <input type="text" name="partnerComment" disabled/>
		        </li>
		        <li>
		            <label for="partnerPhone">${lable.deal.partnerPhone}</label>
		            <input type="text" name="partnerPhone" disabled/>
		        </li>
		        <li>
		            <label for="partnerRegNumber">${lable.deal.partnerRegNumber}</label>
		            <input type="text" name="partnerRegNumber" disabled/>
		        </li>
		        <li>
		            <label for="status">${lable.deal.status}</label>
		            <input type="text" name="status" disabled/>
		        </li>
		        
		        
		        
		        
		       
		        <li>
		        	<button class="submit" type="submit" id="approve"
		        		onclick="
		        				sendDealFormData($('#form-container'), 
		        				'/deal/json/Approve.spr', ${id}); 
		        				return false;">
		        		${lable.deal.approve }
		        	</button>
		        	<button class="submit" type="submit" id="reject"
		        		onclick="
		        			sendDealFormData($('#form-container'), 
		        			'/deal/json/Reject.spr', ${id});
		        			return false;">
		        		${lable.deal.reject }
		        	</button>
		        </li>
		    </ul>
		</form>
	</div>
	<div style="display: table-cell; width: 10%">
		
	</div>
	<div id="map" style="display: table-cell; width: 60%; hight:100%">
		
	</div>
</div>
<script type="text/javascript">
	initDealForm($('.form'), 
			'/deal/json/Form.spr', 
			${id});
</script>