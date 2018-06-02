$(document).ready(function(){
	$.getJSON("/product/json/List.spr", addProducts);
	
	function addProducts(answer){
		var products = answer.data;
		var container = $(".product-cont");
		for (var i = 0; i < products.length; i++){
			addProduct(container, products[i]);
		}
	}
	
	function addProduct(container, product){
		var div = $("<div class='product'>");
		div.css("background-image", "url(" + product.picture + ")");
		div.append($("<h4>" + product.name + "</h4>"))
		
		var aboutProduct = $("<div class='about-product'>")
		
		var sellProduct = $("<div class='sell-product'>");
		sellProduct.append($("<h5>Sell</h5>"));
		sellProduct.append($("<p class='count'>" + product.sellVolume + "</p>"));
		sellProduct.append($("<p class='desc-count'>Average price per ton</p>"));
		sellProduct.append($("<p class='count'>" + product.sellPrice + "</p>"));
		sellProduct.append($("<p class='desc-count'>Requests volume</p>"));
		var sellButton = $("<button class='green-button'>Sell " + product.name + "</button>");
		sellProduct.append(sellButton);
		aboutProduct.append(sellProduct);
		
		var buyProduct = $("<div class='buy-product'>");
		buyProduct.append($("<h5>Buy</h5>"));
		buyProduct.append($("<p class='count'>" + product.buyVolume + "</p>"));
		buyProduct.append($("<p class='desc-count'>Average price per ton</p>"));
		buyProduct.append($("<p class='count'>" + product.buyPrice + "</p>"));
		buyProduct.append($("<p class='desc-count'>Requests volume</p>"));
		var buyButton = $("<button class='blue-button'>Buy " + product.name + "</button>");
		buyProduct.append(buyButton);
		aboutProduct.append(buyProduct);
		
		div.append(aboutProduct);
		container.append(div);
	}
});