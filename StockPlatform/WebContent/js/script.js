$(document).ready(function(){
	if ($('body').width() <= 375) {
		
		$(".burger").click(function(){
			  $(".aside nav.main-menu, .bgdark").fadeToggle();
			  $(".section").toggleClass("section-hidden");
			});
			
			$(document).on('click', function(e) {
				  if (!$(e.target).closest(".aside").length) {
				    $('.aside nav.main-menu, .bgdark').fadeOut();
				  }
				  e.stopPropagation();
			});
		
       
    }


});




