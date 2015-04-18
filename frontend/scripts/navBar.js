//@author: aguler

/*Document scroll on nav-dropdown click*/

$(document).ready(function() {
	$("#0").click(function() {
	    $('html, body').animate({
	        scrollTop: $(".chartcont0").offset().top+13
	    }, 800);
	});

	$("#1").click(function() {
	    $('html, body').animate({
	        scrollTop: $(".chartcont1").offset().top+13
	    }, 800);
	});

	$("#2").click(function() {
	    $('html, body').animate({
	        scrollTop: $(".chartcont2").offset().top+13
	    }, 800);
	});
});