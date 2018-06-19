<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
deal list test page

<p>list of deals: ${ListOfDealsBySellerAndBuyerApproved}</p>
<table border="3">
	<thead>
		<tr>
			<td>product_id</td>
			<td>time</td>
			<td>lotId</td>
			<td>tenderId</td>
			<td>price</td>
			<td>volume</td>
			<td>sellerFoto</td>
			<td>maxTransportPrice</td>
			<td>buyerApproved</td>
			<td>sellerApproved</td>
			<td>buyerAddressId</td>
			<td>sellerAddressId</td>
			<td>buyerDescription</td>
			<td>sellerDescription</td>
			<td>product</td>
			<td>buyerAddress</td>
			<td>sellerAddress</td>
		</tr>
	</thead>
	<tbody>
		<%-- <c:forEach var="deal" items="${ListOfDealsBySellerAndBuyerApproved}">
			<tr>
				<td>deal.productId</td>
				<td>deal.time</td>
				<td>deal.lotId</td>
				<td>deal.tenderId</td>
				<td>deal.price</td>
				<td>deal.volume</td>
				<td>deal.sellerFoto</td>
				<td>deal.maxTransportPrice</td>
				<td>deal.buyerApproved</td>
				<td>deal.sellerApproved</td>
				<td>deal.buyerAddressId</td>
				<td>deal.sellerAddressId</td>
				<td>deal.buyerDescription</td>
				<td>deal.sellerDescription</td>
				<td>deal.product</td>
				<td>deal.buyerAddress</td>
				<td>deal.sellerAddress</td>
			</tr>
		</c:forEach> --%>
	</tbody>
</table>