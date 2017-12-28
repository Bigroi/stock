<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<form class="form" action="#" method="post" name="form">
	    <ul>
	        <li>
	            <label for="name">${lable.product.name}</label>
	            <input type="text" disabled value="${product.name}"/>
	        </li>
	        <li>
	            <label for="description">${lable.product.description}</label>
	            <textarea cols="40" rows="6" disabled>${product.description}</textarea>
	        </li>
	    </ul>
	</form>
	<div id = "lables">lable.lot.min_price,lable.lot.max_volume,lable.tender.max_price,lable.tender.max_volume</div>
	<script>
		$("#lables").localization();
	</script>
	<table>
		<tbody>
			<tr>
				<td>${lable.tradeOffers.lots}: <br>
					<div id = "tableContainerLots">	
						<script>
							$(function(){
								$("#tableContainerLots").tableMaker("/product/json/lots.spr?productId=${product.id}", "#");
							});
						</script>
					</div>
				</td>
				<td>${lable.tradeOffers.tenders}: <br>
					<div id = "tableContainerTenders">	
						<script>
							$(function(){
								$("#tableContainerTenders").tableMaker("/product/json/tenders.spr?productId=${product.id}", "#");
							});
						</script>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<form class="form" action="/product/List.spr" method="post">
		<ul>
			<li>
				<button class="submit" type="submit">${lable.button.back}</button>
			</li>
		</ul>
	</form>