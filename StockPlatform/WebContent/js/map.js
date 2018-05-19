function initMap() {
	$(document).ready(function(){
		var lat = parseFloat($(".latitude")[0].value);
		var lng = parseFloat($(".longitude")[0].value);
		if (window.navigator.userAgent.indexOf("MSIE ") >=0 ||
				window.navigator.userAgent.indexOf("Trident/") >= 0){
			var height = $("#map").parent().parent().parent().height();
			$("#map").css("height", height);
		}
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom: 8,
			center: {
				lat: isNaN(lat)?53.1568911:lat, 
				lng: isNaN(lng)?26.001813399999946:lng
			}
		});
		var geocoder = new google.maps.Geocoder();
		var marker;
		if (!isNaN(lat)){
			marker = new google.maps.Marker({
				map: map,
				position: {
					lat: lat, 
					lng: lng
				}
			});
		} else {
			$("button[type='submit']").prop("disabled", true);
		}
		
		
		$(".country").focusout( function() {
			geocodeAddress(geocoder);
		});
		
		$(".city").focusout( function() {
			geocodeAddress(geocoder);
		});
		
		$(".address").focusout( function() {
			geocodeAddress(geocoder);
		});
	  
		map.addListener('click', function(event) {
			//geocodeCoordinate(event.latLng);
		});
		
		function geocodeCoordinate(location){
			addMarker(location);
			$.get(
				"https://maps.googleapis.com/maps/api/geocode/json", 
				{ 
					latlng: location.lat() + "," + location.lng(), 
					sensor: "true" 
				})
			  .done(function( data ) {
				  var address = data.results[0].formatted_address.trim();
				  var index = address.lastIndexOf(",");
				  var country = address.substr(index + 1);
				  address = address.substr(0, index);
				  index = address.lastIndexOf(",");
				  var city = address.substr(index + 1);
				  address = address.substr(0, index);
				  
				  $(".country")[0].value = country;
				  $(".city")[0].value = city;
				  $(".address")[0].value = address;
				  $("button[type='submit']").prop("disabled", false);
			  });
		}
	
		function geocodeAddress(geocoder) {
			var address = getAddress();
			geocoder.geocode({'address': address}, function(results, status) {
				if (status === 'OK') {
					var location = results[0].geometry.location;
					map.setCenter(location);
					addMarker(location);
					$("button[type='submit']").prop("disabled", false);
				} else {
					if (marker){
						marker.setMap(null);
					}
					$("button[type='submit']").prop("disabled", true);
				}
			});
		}
		
		function addMarker(location){
			if (marker){
				marker.setMap(null);
			}
			marker = new google.maps.Marker({
				map: map,
				position: location
			});
			$(".latitude")[0].value = location.lat();
			$(".longitude")[0].value = location.lng();
		}
		
		function getAddress(){
			return $(".country")[0].value + 
				' ' + $(".city")[0].value +
				' ' + $(".address")[0].value;
		}
	});
}

function initDealMap() {
	$(document).ready(function(){
		var sellerLat = parseFloat($(".seller_latitude")[0].value);
		var sellerLng = parseFloat($(".seller_longitude")[0].value);
		var buyerLat = parseFloat($(".buyer_latitude")[0].value);
		var buyerLng = parseFloat($(".buyer_longitude")[0].value);
		
		
		if (window.navigator.userAgent.indexOf("MSIE ") >=0 ||
				window.navigator.userAgent.indexOf("Trident/") >= 0){
			var height = $("#map").parent().parent().parent().height();
			$("#map").css("height", height);
		}
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom: 8,
			center: {
				lat: (sellerLat + buyerLat) / 2, 
				lng: (sellerLng + buyerLng) / 2
			}
		});
		var geocoder = new google.maps.Geocoder();
		var sellerMarker = new google.maps.Marker({
			map: map,
			position: {
				lat: sellerLat, 
				lng: sellerLng
			}
		});
		var buyerMarker = new google.maps.Marker({
			map: map,
			position: {
				lat: buyerLat, 
				lng: buyerLng
			}
		});
	});
}
	