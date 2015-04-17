//@author: aguler

//Make navigation bar sticky on scroll

$(document).ready(function() {
	var navOff = $("#header").offset().top;
	var navStyleOrig = {"height": "35px"};
	var navStyleScroll = {"margin-left": "100000px", "height": "35px"};
	$("#header-wrapper").height(jQuery("#header").outerHeight());

	$(window).scroll(function() {

		var scrollPos = jQuery(window).scrollTop();

		if (scrollPos >= navOff) {

			//TODO:add title next to nav-bar
			$("#header").addClass("sticky");
			$("nav").css(navStyleScroll);
		}else {
			$("#header").removeClass("sticky");
			$("#header").addClass("headline");

			$("nav").css(navStyleOrig);

		}
	});

});