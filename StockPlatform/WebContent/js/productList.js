$(document).ready(function(){
	if ($(".product-cont").length > 0){
		$.getJSON("/product/json/List.spr", addProducts);
	}
	
	function addProducts(answer){
		var products = answer.data;
		var container = $(".product-cont");
		for (var i = 0; i < products.length; i++){
			addProduct(container, products[i]);
		}
	}

	function addProduct(container, product){
		var authorised = container.hasClass("authorised");
		var div = $("<div class='product'>");
		div.css("background-image", "url(" + product.picture + ")");
		div.append($("<h4>" + product.name + "</h4>"))
		
		var aboutProduct = $("<div class='about-product'>")
		
		var sellProduct = $("<div class='sell-product'>");
		sellProduct.append($("<h5>" + l10n.translate("label.index.sell") +"</h5>"));
		sellProduct.append($("<p class='count'>" + product.sellVolume + "</p>"));
		sellProduct.append($("<p class='desc-count'>" + l10n.translate("label.index.requests_volume") + "</p>"));
		sellProduct.append($("<p class='count'>" + product.sellPrice + "</p>"));
		sellProduct.append($("<p class='desc-count'>" + l10n.translate("label.index.average_price") + "</p>"));
		if (!authorised){
			var sellButton = $("<button class='green-button'>" + l10n.translate("label.index.sell") + " " + product.name + "</button>");
			sellProduct.append(sellButton);
			sellButton.on("click", function(){
				showDialog(getReginDialogParams());
			});
		}
		aboutProduct.append(sellProduct);
		
		var buyProduct = $("<div class='buy-product'>");
		buyProduct.append($("<h5>" + l10n.translate("label.index.buy") +"</h5>"));
		buyProduct.append($("<p class='count'>" + product.buyVolume + "</p>"));
		buyProduct.append($("<p class='desc-count'>" + l10n.translate("label.index.requests_volume") + "</p>"));
		buyProduct.append($("<p class='count'>" + product.buyPrice + "</p>"));
		buyProduct.append($("<p class='desc-count'>" + l10n.translate("label.index.average_price") + "</p>"));
		if (!authorised){
			var buyButton = $("<button class='blue-button'>" + l10n.translate("label.index.buy") +" " + product.name + "</button>");
			buyProduct.append(buyButton);
			buyButton.on("click", function(){
				showDialog(getReginDialogParams());
			});
		}
		aboutProduct.append(buyProduct);
		div.append(aboutProduct);
		if (authorised){
			var details = $("<button class='blue-button'>" + l10n.translate("label.product.details") +" " + product.name + "</button>");
			div.append(details);
			details.on("click", function(){
				document.location = "/product/TradeOffers.spr?id=" + product.id;
			});
		}
		container.append(div);
	}
});