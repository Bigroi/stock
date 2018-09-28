$(document).ready(function(){
	
	$( function() {
	    $.widget( "custom.iconselectmenu", $.ui.selectmenu, {
	      _renderItem: function( ul, item ) {
	        var li = $( "<li>" ),
	          wrapper = $( "<div>", { text: item.label } );
	        if ( item.disabled ) {li.addClass( "ui-state-disabled" );}
	        $( "<span>", {style: item.element.attr( "data-style" ),"class": "ui-icon " + item.element.attr( "data-class" )
	        }).appendTo( wrapper );
	        return li.append( wrapper ).appendTo( ul );
	      }
	    });
	    $("#languages-select").iconselectmenu().iconselectmenu("menuWidget").addClass("ui-menu-icons customicons");
	    var selectedImage = $(this).find("option:selected").attr("data-class");
	    $(".ui-selectmenu-text").css('background-image', 'url("/Static/img/' + selectedImage +'.png")');
	    $( "#languages-select" ).iconselectmenu({
	    	select: function(event, ui){ 
		        var selectedImage = $(this).find("option:selected").attr("data-class");
		        $(".ui-selectmenu-text").css('background-image', 'url("/Static/img/' + selectedImage +'.png")');
		        changeLanguage();
	    	}
	    });
	});
	
	baguetteBox.run('.gallery', {
		captions: false
	});
	
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
	$(".bgdark").click(function(){
		  $(".bgdark").hide();
		  $(".aside").removeClass("aside-adaptive");
	});
	$(document).on("click", '.burger', function() {
		$(".aside").addClass("aside-adaptive");
		 $(".bgdark").show();
	});
	$(document).on("click", '.burger-close', function() {
		$(".aside").removeClass("aside-adaptive");
		$(".bgdark").hide();
	});

	/*if ($('body').width() <= 1280) {
		$(".burger").click(function(){
			  $(".bgdark").fadeToggle();
			  $("body").toggleClass("s-hidden");
			});
		$(".bgdark").click(function(){
			  $(".aside nav.main-menu, .bgdark").hide();
			});
    }*/
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
