//@author: Alan Güler
//@date: 2015-04-01

//Make navigation bar sticky on scroll

jQuery(document).ready(function() {
	var navOff = jQuery("#header").offset().top;
	
	jQuery("#header-wrapper").height(jQuery("#header").outerHeight());
	jQuery(window).scroll(function() {

		var scrollPos = jQuery(window).scrollTop();

		if (scrollPos >= navOff) {
			jQuery("#header").addClass("sticky");
		}else {
			jQuery("#header").removeClass("sticky");
		}
	});

});