<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<p>${lable.productForm.name}: ${product.name}</p>
	<p>${lable.productForm.description}: ${product.description}</p>
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
	<form action="/product/List.spr">
		<input type="submit" value="{lable.button.back}">
	</form>