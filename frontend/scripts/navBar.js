function navInit(jsobarr) {
	var ani = 800;
	for (var i = 0; i < getChartLen(); i++) {
		$("#"+i).text(jsobarr[i].category.capitalize());
	}

	$("#0").click(function() {
	    $('html, body').animate({
	        scrollTop: $(".chartcont0").offset().top
	    }, ani);
	});

	$("#1").click(function() {
	    $('html, body').animate({
	        scrollTop: $(".chartcont1").offset().top
	    }, ani);
	});

	$("#2").click(function() {
	    $('html, body').animate({
	        scrollTop: $(".chartcont2").offset().top
	    }, ani);
	});
}