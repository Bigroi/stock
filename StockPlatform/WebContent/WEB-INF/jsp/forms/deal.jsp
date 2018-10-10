<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
 	<div class="white-div white-div-deal">
    	<form class="form" id="deal-form" data-url="/deal/json/Form.spr" data-id="${id}" action="#" method="post" name="form">
			<input type="hidden" name="sellerAddrress.latitude" id="seller_latitude">
			<input type="hidden" name="sellerAddrress.longitude" id="seller_longitude">
			<input type="hidden" name="buyerAddrress.latitude" id="buyer_lalitude">
			<input type="hidden" name="buyerAddrress.longitude" id="buyer_longitude">
			<input type="hidden" name="id" id="dealId">
			<div>
				<div class="dialogbox-message"></div>
			</div>
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
					<ul>
				        <li>
				            <label for="partnerName">${label.deal.partnerName}</label>
				            <input type="text" name="partnerAddress.company.name" disabled/>
				            <div class="partner-mark"></div>
		            		<div class="reviewStarsDeal">
							    <input id="star-5" type="radio" name="reviewStars" disabled>
							    <label title="gorgeous" for="star-5"></label>
						
							    <input id="star-4" type="radio" name="reviewStars" disabled>
							    <label title="good" for="star-4"></label>
							
							    <input id="star-3" type="radio" name="reviewStars" checked disabled>
							    <label title="regular" for="star-3"></label>
							
							    <input id="star-2" type="radio" name="reviewStars" disabled>
							    <label title="poor" for="star-2"></label>
							
							    <input id="star-1" type="radio" name="reviewStars" disabled>
							    <label title="bad" for="star-1"></label>
							</div>
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
				        <li>
				            <label>${label.deal.sellerFoto}</label>
				            <div class="sellerFoto">
				            	<div class="gallery">
								    <a href="/Static/img/bg-platform.png" data-caption="Image caption">
								        <img name="sellerFoto" src="" alt="First image">
								    </a>
								</div>
				            </div>
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
		        	
		        	<%-- <button class="submit blue-button deal-button" type="submit" id="transport-button"
		        		onclick="
		        				return sendDealFormData(
			        					'/deal/json/Transport.spr'
			        					); ">
		        		${label.deal.transport }
		        	</button> --%>
		        	
		        	<button class="submit blue-button deal-button" type="submit" id="reject-button"
		        		onclick="
		        			return sendDealFormData( 
		        						'/deal/json/Reject.spr'
		        					);">
		        		${label.deal.reject }
		        	</button>
		        	
		        	<button class="submit blue-button deal-feedback" type="button" id="feedback-button" style="display: none;">
		        		${label.button.feedback }
		        	</button>
				</div>
			</div>
		</form>
    </div>

