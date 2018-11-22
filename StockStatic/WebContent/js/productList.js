$(document).ready(function(){
	if ($(".product-cont").length > 0){
		$.getJSON(getContextRoot() + "/product/json/List.spr", addProducts);
	}
	
	function addProducts(answer){
		var products = answer.data;
		var container = $(".product-cont");
		for (var i = 0; i < products.length; i++){
			addProduct(container, products[i]);
		}
    	var width = $(window).width();
    	container.bxSlider({
	    	  maxSlides: (width < 1280) ? 1 : 2,
	    	  minSlides:(width < 1280) ? 1 : 2,
	    	  slideWidth:(width < 1280) ? 720 : 510,
	    	  pager: false,
	    	  auto: true
	      });
    	$(document).on("click", '.bx-prev, .bx-next', function() {
    		container.stopAuto();
    		container.startAuto();
    	});
	}

	function addProduct(container, product){
		var authorised = container.hasClass("authorised");
		var div = $("<div class='product'>");
		div.css("background-image", "url(" + product.picture + ")");
		div.append($("<h4>" + product.name + "</h4>"))
		
		var aboutProduct = $("<div class='about-product'>")
		
		var sellProduct = $("<div class='sell-product'>");
		sellProduct.append($("<h5>" + l10n.translate("label.index.sell") +"</h5>"));
		var buyProduct = $("<div class='buy-product'>");
		buyProduct.append($("<h5>" + l10n.translate("label.index.buy") +"</h5>"));
		
		if(!authorised){
			sellProduct.append($("<p class='count'>" + product.sellVolume + "</p>"));
			sellProduct.append($("<p class='desc-count'>" + l10n.translate("label.index.requests_volume") + "</p>"));
			sellProduct.append($("<p class='count'>" + product.sellPrice + "</p>"));
			sellProduct.append($("<p class='desc-count'>" + l10n.translate("label.index.average_price") + "</p>"));
			aboutProduct.append(sellProduct);
			buyProduct.append($("<p class='count'>" + product.buyVolume + "</p>"));
			buyProduct.append($("<p class='desc-count'>" + l10n.translate("label.index.requests_volume") + "</p>"));
			buyProduct.append($("<p class='count'>" + product.buyPrice + "</p>"));
			buyProduct.append($("<p class='desc-count'>" + l10n.translate("label.index.average_price") + "</p>"));
			aboutProduct.append(buyProduct);
			
			var sellButton = $("<button class='background-green'>" + 
					l10n.translate("label.index.sell") + " " + 
					product.name + "</button>");
			sellProduct.append(sellButton);
			sellButton.on("click", function(){
				showDialog(getReginDialogParams());
			});
			
			var buyButton = $("<button class='background-blue'>" + 
					l10n.translate("label.index.buy") +" " + 
					product.name + "</button>");
			buyProduct.append(buyButton);
			buyButton.on("click", function(){
				showDialog(getReginDialogParams());
			});
			
		}
		if (authorised){
			var tableProduct = $("<table>");
			var tableTheadProduct = $("<thead><tr><td></td><td>" + l10n.translate("label.index.average_price") + "</td><td>" + l10n.translate("label.index.requests_volume") + "</td></tr></thead>");
			tableProduct.append(tableTheadProduct);
			var tableTbodyProduct = $("<tbody>");
			tableProduct.append(tableTbodyProduct);
			
			var trSellProduct = $("<tr class='sell-tr'>");
			tableTbodyProduct.append(trSellProduct);

			trSellProduct.append($("<td>" + l10n.translate("label.index.sell") + "</td>"));
			trSellProduct.append($("<td>" + product.sellPrice + "</td>"));
			trSellProduct.append($("<td>" + product.sellVolume + "</td>"));
			
			var trBuyProduct = $("<tr class='buy-tr'>");
			tableTbodyProduct.append(trBuyProduct);
			trBuyProduct.append($("<td>" + l10n.translate("label.index.buy") + "</td>"));
			trBuyProduct.append($("<td>" + product.buyPrice + "</td>"));
			trBuyProduct.append($("<td>" + product.buyVolume + "</td>"));
			
			aboutProduct.append(tableProduct);
			
			var details = $("<button class='background-blue'>" + 
					l10n.translate("label.product.details") + " " + 
					product.name + "</button>");
			div.append(details);
			details.on("click", function(){
				document.location = getContextRoot() + "/product/TradeOffers.spr?id=" + product.id;
			});
		}
		div.append(aboutProduct);
		container.append(div);
	}
});