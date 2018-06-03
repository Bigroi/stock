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
		sellProduct.append($("<h5>" + translate("label.index.sell") +"</h5>"));
		sellProduct.append($("<p class='count'>" + product.sellVolume + "</p>"));
		sellProduct.append($("<p class='desc-count'>" + translate("label.index.requests_volume") + "</p>"));
		sellProduct.append($("<p class='count'>" + product.sellPrice + "</p>"));
		sellProduct.append($("<p class='desc-count'>" + translate("label.index.average_price") + "</p>"));
		var sellButton = $("<button class='green-button'>" + translate("label.index.sell") + " " + product.name + "</button>");
		sellProduct.append(sellButton);
		aboutProduct.append(sellProduct);
		
		var buyProduct = $("<div class='buy-product'>");
		buyProduct.append($("<h5>" + translate("label.index.buy") +"</h5>"));
		buyProduct.append($("<p class='count'>" + product.buyVolume + "</p>"));
		buyProduct.append($("<p class='desc-count'>" + translate("label.index.requests_volume") + "</p>"));
		buyProduct.append($("<p class='count'>" + product.buyPrice + "</p>"));
		buyProduct.append($("<p class='desc-count'>" + translate("label.index.average_price") + "</p>"));
		var buyButton = $("<button class='blue-button'>" + translate("label.index.buy") +" " + product.name + "</button>");
		buyProduct.append(buyButton);
		aboutProduct.append(buyProduct);
		
		div.append(aboutProduct);
		container.append(div);
	}
});