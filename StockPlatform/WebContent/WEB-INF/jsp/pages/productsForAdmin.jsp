<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div id="product-form-container"></div>
<h1>${lable.productsForAdmin.title}</h1>
<div id = "lables">lable.product.name,lable.product.description,lable.product.archive</div>
<script>
$("#lables").localization();
</script>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/product/json/admin/List.spr", setProductDialogPlugin);
		});
	</script>
</div>
<form action="#" class="form">
	<ul>
		<li>
			<button class="submit" id="new-product-button">${lable.button.create}</button>
			<script type="text/javascript">setProductDialogPlugin($("#new-product-button"))</script>
		</li>
	</ul>
</form>
<br>
