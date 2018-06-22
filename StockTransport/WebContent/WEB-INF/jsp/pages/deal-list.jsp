<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>deal list:</h2>
<table border="3">
	<thead>
		<tr>
			<td>deal_id</td>
			<td>deal_productId</td>
			<td>deal_time</td>
			<td>deal_lotId</td>
			<td>deal_tenderId</td>
			<td>deal_price</td>
			<td>deal_volume</td>
			<td>deal_sellerFoto</td>
			<td>deal_maxTransportPrice</td>
			<td>deal_buyerChoice</td>
			<td>deal_sellerChoice</td>
			<td>deal_buyerAddressId</td>
			<td>deal_sellerAddressId</td>
			<td>deal_buyerDescription</td>
			<td>deal_sellerDescription</td>
			<td>prod_id</td>
			<td>prod_name</td>
		    <td>prod_description</td>
			<td>prod_removed</td>
			<td>prod_delivaryPrice</td>
			<td>bAddress_id</td>
			<td>bAddress_city</td>
			<td>bAddress_country</td>
			<td>bAdress_address</td>
			<td>bAdress_latitude</td>
			<td>bAdress_longitude</td>
			<td>bAdress_companyId</td>
			<td>sAddres_id</td>
			<td>sAddress_city</td>
			<td>sAddress_country</td>
			<td>sAdress_address</td>
			<td>sAdress_latitude</td>
			<td>sAdress_longitude</td>
			<td>sAdress_companyId</td>
		</tr>
	</thead>
	<tbody>
		 <c:forEach var="dealList" items="${ListOfDealsBySellerAndBuyerApproved}">
			<tr>
				<td>${dealList.id}</td>
				<td>${dealList.productId}</td>
				<td>${dealList.time}</td>
				<td>${dealList.lotId}</td>
				<td>${dealList.tenderId}</td>
				<td>${dealList.price}</td>
				<td>${dealList.volume}</td>
				<td>${dealList.sellerFoto}</td>
				<td>${dealList.maxTransportPrice}</td>
				<td>${dealList.buyerChoice}</td>
				<td>${dealList.sellerChoice}</td>
				<td>${dealList.buyerAddressId}</td>
				<td>${dealList.sellerAddressId}</td>
				<td>${dealList.buyerDescription}</td>
				<td>${dealList.sellerDescription}</td>
				<td>${dealList.product.id}</td>
				<td>${dealList.product.name}</td>
				<td>${dealList.product.description}</td>
				<td>${dealList.product.removed}</td>
				<td>${dealList.product.delivaryPrice}</td>
				<td>${dealList.buyerAddress.id}</td>
				<td>${dealList.buyerAddress.city}</td>
				<td>${dealList.buyerAddress.country}</td>
				<td>${dealList.buyerAddress.address}</td>
				<td>${dealList.buyerAddress.latitude}</td>
				<td>${dealList.buyerAddress.longitude}</td>
				<td>${dealList.buyerAddress.companyId}</td>
				<td>${dealList.sellerAddress.id}</td> 
				<td>${dealList.sellerAddress.city}</td>
				<td>${dealList.sellerAddress.country}</td>
				<td>${dealList.sellerAddress.address}</td>
				<td>${dealList.sellerAddress.latitude}</td>
				<td>${dealList.sellerAddress.longitude}</td>
				<td>${dealList.sellerAddress.companyId}</td>
			</tr>
		</c:forEach>  
	</tbody>
</table>