<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div id="product-form-container"></div>
<h1>${label.productsForAdmin.title}</h1>
<form action="#" class="form">
	<ul>
		<li>
			<button class="submit" id="new-product-button">${label.button.create}</button>
			<script type="text/javascript">setProductDialogPlugin($("#new-product-button"))</script>
		</li>
	</ul>
</form>
<div id = "tableContainer">	
	<script>
		$(function(){
			$("#tableContainer").tableMaker("/product/json/admin/List.spr", setProductDialogPlugin);
		});
	</script>
</div>
