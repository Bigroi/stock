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
		var count = 0;
		var counter = false;
		var isRunning = true;
		function interval(){
		    counter = setInterval(function(){
		    	if (isRunning) {
		    		count++;
					if(count == 1800){
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
		    interval();
		});
		$(".dialogbox-session .dialogbox-spanClose, .session-out, .dialogbox-session").click(function(){
			$(".dialogbox-session").hide();
			window.location.replace("/account/json/Logout.spr");
		});
	}
});
