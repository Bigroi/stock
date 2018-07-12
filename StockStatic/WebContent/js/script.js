$(document).ready(function(){
	$(document).on("click", '#continue', function() {
		if($('#registration-form')[0].checkValidity()){
			$(".registration-first-part").hide();
			$(".registration-second-part").show();
			$(".registration-second-part input").attr("required", "required");
			return false;
		} else {
			return true;
		}
	});
	$(document).on("click", '.login-button-page', function() {
		$(".login-list").fadeToggle();
	});
	$(document).on('click', function(e) {
		  if (!$(e.target).closest(".login-box").length) {
		    $('.login-list').fadeOut();
		  }
		  e.stopPropagation();
	});
	$(document).on("click", '#go-back', function() {
		$(".registration-first-part").show();
		$(".registration-second-part").hide();
		$(".registration-second-part input").removeAttr("required");
	});
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
									window.location = getContextRoot() + "/account/json/Logout.spr";
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
	var menu_selector = "header nav"; 
	function onScroll(){
		var scroll_top = $(document).scrollTop();
		$(menu_selector + " a").each(function(){
			var hash = $(this).attr("href");
			var target = $(hash);
			if (target.position().top - 78 <= scroll_top && target.position().top + target.outerHeight() > scroll_top) {
				$(menu_selector + " a.active").removeClass("active");
				$(this).addClass("active");
			} else {
				$(this).removeClass("active");
			}
		});
	}
	$(document).on("scroll", onScroll);
	$("header nav a").click(function(e){
		e.preventDefault();
		$(document).off("scroll");
		$(menu_selector + " a.active").removeClass("active");
		$(this).addClass("active");
		var hash = $(this).attr("href");
		var target = $(hash);
		$("html, body").animate({
		    scrollTop: target.offset().top - 78
			}, 500, function(){
		});
	});
});
