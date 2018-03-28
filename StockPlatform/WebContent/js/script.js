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
						showMessageDialog(
								"label.login.loginAgain", 
								"warning",
								function(){
									window.location = "/account/json/Logout.spr";
								});
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
	}
	if($("td").is(".dataTables_empty")) {
		console.log('без пагинаци');
		//Марго! русский текст!
	}
	
	
});
