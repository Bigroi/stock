function initRegistrationMap(){
	$(document).ready(function(){
		var $googleMapsContainer = $(".google-map-container");
		$googleMapsContainer.css("background-image", "");
		var map = new google.maps.Map($googleMapsContainer[0], {
			zoom: 8,
			center: {
				lat: 53.1568911, 
				lng: 26.001813399999946
			}
		});
		var geocoder = new google.maps.Geocoder();
		
		
		$(".country").focusout( function() {
			geocodeAddress(geocoder);
		});
		
		$(".city").focusout( function() {
			geocodeAddress(geocoder);
		});
		
		$(".address").focusout( function() {
			geocodeAddress(geocoder);
		});
		
		var marker;
		
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
				}
			});
		}
		
		function getAddress(){
			return $(".country")[0].value + 
				' ' + $(".city")[0].value +
				' ' + $(".address")[0].value;
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
		
	});
}






//old functions
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
		var sellerLat = parseFloat($("#seller_latitude")[0].value);
		var sellerLng = parseFloat($("#seller_longitude")[0].value);
		var buyerLat = parseFloat($("#buyer_lalitude")[0].value);
		var buyerLng = parseFloat($("#buyer_longitude")[0].value);
		
		
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
		var directionsService = new google.maps.DirectionsService;
        var directionsDisplay = new google.maps.DirectionsRenderer;
        directionsDisplay.setMap(map);
        
        calculateAndDisplayRoute(directionsService, directionsDisplay, 
        		new google.maps.LatLng(sellerLat, sellerLng), new google.maps.LatLng(buyerLat, buyerLng))
	});
	
	function calculateAndDisplayRoute(directionsService, directionsDisplay, startPoint, endPoint) {
        directionsService.route({
          origin: startPoint,
          destination: endPoint,
          travelMode: 'DRIVING'
        }, function(response, status) {
          if (status === 'OK') {
            directionsDisplay.setDirections(response);
          } else {
            window.alert('Directions request failed due to ' + status);
          }
        });
      }
}
	