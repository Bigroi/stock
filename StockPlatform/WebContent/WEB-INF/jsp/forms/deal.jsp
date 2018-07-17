<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- <div id="table-container">
		<table id="main-table" data-url="/Transport/proposition/json/Propositions.spr"></table>
	</div><br> -->
	
 	<div class="white-div white-div-deal">
    	<form class="form" id="deal-form" data-url="/deal/json/Form.spr" data-id="${id}" action="#" method="post" name="form">
			<input type="hidden" name="sellerAddrress.latitude" id="seller_latitude">
			<input type="hidden" name="sellerAddrress.longitude" id="seller_longitude">
			<input type="hidden" name="buyerAddrress.latitude" id="buyer_lalitude">
			<input type="hidden" name="buyerAddrress.longitude" id="buyer_longitude">
			<input type="hidden" name="id">
			<div class="header-white-div">
				<ul>
			        <li>
			            <label for="productName">${label.deal.productName}</label>
			            <input type="text" name="productName" disabled/>
			        </li>
			        <li>
			            <label for="price">${label.deal.price}</label>
			            <input type="text" name="price" disabled/>
			        </li>
			        <li>
			            <label for="volume">${label.deal.volume}</label>
			            <input type="text" name="volume" disabled/>
			        </li>
			         <li>
			            <label for="time">${label.deal.time}</label>
			            <input type="text" name="time" disabled/>
			        </li>
			     </ul>
		   </div>
		   <div class="deal-status">
		   		<label for="status">${label.deal.status}</label>
			    <input type="text" name="status" disabled/>
		   </div>
		   <div class="body-deal-form">	
				<div>
					<div class="form-message"></div>
					<ul>
				        <li>
				            <label for="partnerName">${label.deal.partnerName}</label>
				            <input type="text" name="partnerAddress.company.name" disabled/>
				        </li>
	
				        <li>
				            <label for="partnerComment">${label.deal.partnerComment}</label>
				            <input type="text" name="partnerDescription" disabled/>
				        </li>
				        <li>
				            <label for="partnerPhone">${label.deal.partnerPhone}</label>
				            <input type="text" name="partnerAddress.company.phone" disabled/>
				        </li>
				        <li>
				            <label for="partnerRegNumber">${label.deal.partnerRegNumber}</label>
				            <input type="text" name="partnerAddress.company.regNumber" disabled/>
				        </li>
				    </ul>
				</div>
				<div>
					<ul>
						<li>
					        <label for="partnerAddress">${label.deal.partnerAddress}</label>
					        <textarea name="partnerAddress.address" disabled></textarea>
					    </li>
					</ul>
					<div class="map-deal-form" style=" ">				        
						<div style="bottom:0;top: 0;right: 0; left: 0; position: absolute;" id="map-mob-deal">
							<div id="map" style="width: 100%; height: 100%"></div>
						</div>
					</div>
				</div>
			</div>
				<div class="footer-deal-form">
				<div>
					<button class="submit gray-button"  id="back-deals"
		        		onclick ="document.location = '/deal/MyDeals.spr'; return false">
		        		${label.button.back }
		        	</button>
				</div>
				<div>
					<button class="submit blue-button deal-button" type="submit" id="approve-button"
		        		onclick="
		        				return sendDealFormData(
			        					'/deal/json/Approve.spr'
			        					); ">
		        		${label.deal.approve }
		        	</button>
		        	
		        	<button class="submit blue-button deal-button" type="submit" id="transport-button"
		        		onclick="
		        				return sendDealFormData(
			        					'/deal/json/Transport.spr'
			        					); ">
		        		${label.deal.transport }
		        	</button>
		        	
		        	<button class="submit blue-button deal-button" type="submit" id="reject-button"
		        		onclick="
		        			return sendDealFormData( 
		        						'/deal/json/Reject.spr'
		        					);">
		        		${label.deal.reject }
		        	</button>
				</div>
			</div>
		</form>
    </div>

