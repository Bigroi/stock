<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
 	<div class="white-div white-div-deal">
    	<form class="form" id="deal-form" data-url="/deal/json/Form" data-id="${id}" action="#" method="post" name="form">
			<input type="hidden" name="sellerLatitude" id="seller_latitude">
			<input type="hidden" name="sellerLongitude" id="seller_longitude">
			<input type="hidden" name="buyerLatitude" id="buyer_lalitude">
			<input type="hidden" name="buyerLongitude" id="buyer_longitude">
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
			            <label for="productName">${label.deal.categoryName}</label>
			            <input type="text" name="categoryName" disabled/>
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
				            <input type="text" name="partnerCompanyName" disabled/>
		            		<div class="reviewStarsDeal">
							    <input id="star5" type="radio" name="star5" disabled>
							    <label title="gorgeous" for="star5"></label>
						
							    <input id="star4" type="radio" name="star4" disabled>
							    <label title="good" for="star4"></label>
							
							    <input id="star_3" type="radio" name="star3" disabled>
							    <label title="regular" for="star3"></label>
							
							    <input id="star_2" type="radio" name="star2" disabled>
							    <label title="poor" for="star2"></label>
							
							    <input id="star_1" type="radio" name="star1" disabled>
							    <label title="bad" for="star1"></label>
							</div>
				        </li>
	
				        <li>
				            <label for="partnerDescription">${label.deal.partnerComment}</label>
				            <input type="text" name="partnerDescription" disabled/>
				        </li>
				        <li>
				            <label for="packaging">${label.deal.packaging}</label>
				            <input type="text" name="packaging" disabled/>
				        </li>
				        <li>
				            <label for="processing">${label.deal.processing}</label>
				            <input type="text" name="processing" disabled/>
				        </li>
				        <li>
				            <label for="partnerPhone">${label.deal.partnerPhone}</label>
				            <input type="text" name="partnerPhone" disabled/>
				        </li>
				        <li>
				            <label for="partnerRegNumber">${label.deal.partnerRegNumber}</label>
				            <input type="text" name="partnerRegNumber" disabled/>
				        </li>
				        <li>
				            <label for="foto">${label.deal.sellerFoto}</label>
				            <div class="sellerFoto">
				            	<div class="gallery">
								    <a href="${pageContext.request.contextPath}/deal/json/Picture?dealId=${id}">
								        <img name="foto" src="">
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
					        <textarea name="partnerAddress" disabled></textarea>
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
		        		onclick ="document.location = '/deal/MyDeals'; return false">
		        		${label.button.back }
		        	</button>
				</div>
				<div>
					<button class="submit blue-button deal-button" type="submit" id="approve-button"
		        		onclick="return sendDealFormData('/deal/json/Approve'); ">
		        		${label.deal.approve }
		        	</button>
		        	
		        	<button class="submit blue-button deal-button" type="submit" id="reject-button"
		        		onclick="return sendDealFormData('/deal/json/Reject');">
		        		${label.deal.reject }
		        	</button>
		        	
		        	<button class="submit blue-button deal-feedback" type="button" id="feedback-button">
		        		${label.button.feedback }
		        	</button>
				</div>
			</div>
		</form>
    </div>

