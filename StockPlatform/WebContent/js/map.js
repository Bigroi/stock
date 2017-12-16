function initMap() {
	var lat = parseFloat($("input[name='latitude']")[0].value);
	var lng = parseFloat($("input[name='longitude']")[0].value);
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
	
	$("input[name='address']").focusout( function() {
		geocodeAddress(geocoder);
	});
  
	map.addListener('click', function(event) {
		geocodeCoordinate(event.latLng);
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
			  
			  $("input[name='country']")[0].value = country;
			  $("input[name='city']")[0].value = city;
			  $("input[name='address']")[0].value = address;
			  $("input[type='submit']").removeAttr("disabled");
		  });
	}

	function geocodeAddress(geocoder) {
		var address = getAddress();
		geocoder.geocode({'address': address}, function(results, status) {
			if (status === 'OK') {
				var location = results[0].geometry.location;
				map.setCenter(location);
				addMarker(location);
				$("input[type='submit']").removeAttr("disabled");
			} else {
				marker.setMap(null);
				$("input[type='submit']").attr("disabled", "disabled");
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
		$("input[name='latitude']")[0].value = location.lat();
		$("input[name='longitude']")[0].value = location.lng();
	}
	
	function getAddress(){
		return $("input[name='country']")[0].value + 
			' ' + $("input[name='city']")[0].value +
			' ' + $("input[name='address']")[0].value;
	}
}