function initMap() {
	var lat = parseFloat($("input[name='width']")[0].value);
	var lng = parseFloat($("input[name='length']")[0].value);
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 8,
		center: {
			lat: lat, 
			lng: lng
		}
	});
	
	var geocoder = new google.maps.Geocoder();
	var marker = new google.maps.Marker({
		map: map,
		position: {
			lat: lat, 
			lng: lng
		}
	});
	
	
	$("input[name='country']").focusout( function() {
		geocodeAddress(geocoder);
	});
	
	$("input[name='city']").focusout( function() {
		geocodeAddress(geocoder);
	});
  
	map.addListener('click', function(event) {
		addMarker(event.latLng);
	});

	function geocodeAddress(geocoder) {
		var address = getAddress();
		geocoder.geocode({'address': address}, function(results, status) {
			if (status === 'OK') {
				var location = results[0].geometry.location;
				map.setCenter(location);
				addMarker(location);
			} else {
				alert('Geocode was not successful for the following reason: ' + status);
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
		$("input[name='width']")[0].value = location.lat();
		$("input[name='length']")[0].value = location.lng();
	}
	
	function getAddress(){
		return $("input[name='country']")[0].value + ' ' + $("input[name='city']")[0].value;
	}
}