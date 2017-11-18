<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<p>Product name: ${product.name}</p>
	<p>Description: ${product.description}</p>
	<table>
		<tbody>
			<tr>
				<td>Lots: <br>
					<div id = "tableContainerLots">	
						<script>
							$(function(){
								$("#tableContainerLots").tableMaker("/product/json/lots.spr?productId=${product.id}", "#");
							});
						</script>
					</div>
				</td>
				<td>Tenders: <br>
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
		<input type="submit" value="Product list">
	</form>
	<form action="/Index.spr">
		<input type="submit" value="Welcome page">
	</form>