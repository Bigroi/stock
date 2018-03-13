$(document).ready(function(){
	if ($('body').width() <= 900) {
		$(".burger").click(function(){
			  $(".aside nav.main-menu, .bgdark").fadeToggle();
			  $("body").toggleClass("s-hidden");
			});
		$(".bgdark").click(function(){
			  $(".aside nav.main-menu, .bgdark").hide();
			});
    }
	if($("a").is("#session-start")){
		console.log("session-start");
		var count = 0;
		var counter = false;
		var isRunning = true;
		function interval(){
		    counter = setInterval(function(){
		    	if (isRunning) {
		    		count++;
			        console.log(count);
					if(count == 1800){
						console.log("333");
						$(".dialogbox-session").show();
						isRunning = false;
					}
		        }
		    },1000);
		}
		interval();
		$(document).on("keydown click mousemove", document, function() {
		    clearInterval(counter);
		    count = 0;
		    console.log(count);
		    interval();
		});
		$(".dialogbox-session .dialogbox-spanClose, .session-out, .dialogbox-session").click(function(){
			$(".dialogbox-session").hide();
			window.location.replace("http://localhost:8080/account/json/Logout.spr");
		});
	}
});
