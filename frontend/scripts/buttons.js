//@author: aguler


var BUTTONS_URL = "url(../img/partiikoner.png)";

function Buttons(chartid) {
	this.name = ".chartcont" + chartid + " .buttons";
	this.parties = [".vp",".s", ".mp", ".c", ".fp",".kd",".m", ".sd"];
	this.htmllines = "";
	for (var i = 0; i < this.parties.length; i++) {
		this.htmllines = this.htmllines + "<li class=" + this.parties[i].substring(1) + "></li>";
	}
}

/*Add children to class buttons and add css properties to 
/*every child. */

Buttons.prototype.init = function() {
	//add html classes
	$(this.name).append(this.htmllines);

	//add css properties
	var x = 0;
	var y = 0;
	for (var i = 0; i < this.parties.length; i++) {
		var style = {"background": BUTTONS_URL + x + "px" + " " + y + "px"};
		$(this.name + " " + this.parties[i]).css(style);
		//go down 40 pixles in spritesheet
		y = y - 40;
	}
}

function Button(id, chartid, bname, name, click) {
	this.name = bname + " " + name;
	this.party = name.substring(1);
	this.chartname = "chart" + chartid;
	this.click = click;
	this.x = 0;
	this.y = -id*40;
}

Button.prototype = {
	init: function() {
		var b = this;
		if (this.click == true) {
			this.update(this.name, 80);
			add(this.chartname, this.party);
		}
		this.status();
	},
	/*Status uses jQuery's event handler to check for mouseevents*/
	status: function() {
		var b = this;
		$(b.name).on({
			mouseleave: function() {
				if (b.click != true) b.update(this, 0);
			},
			mouseenter: function() {
				if (b.click != true) b.update(this, 40);
			},
			click: function() {
				b.update(this, 80);
				
				if (b.click == true) {
					b.update(this, 0);
					destroy(b.chartname, b.party);
				}else {
					add(b.chartname, b.party);
				}
				b.click ^= true;
			}
		});
	},
	update: function(c, xpix) {
		var style = {"background": BUTTONS_URL + (this.x + xpix) + "px" + " " + this.y + "px"};
		$(c).css(style);
	},
	addClick: function() {
		this.click = true;
	}
}


function isIndex(){
    var pagePath= window.location.pathname;
    if (pagePath.substring(pagePath.lastIndexOf("/") + 1) == "index.html") {
    	return true;
    }
    return false;

}

/*This function should check if the button should be clicked on page load*/
function isClicked(party) {
	return false;
}

function buttonsInit() {
	var bs = [];
	var chartlen = getChartLen();
	for (var i = 0; i < chartlen; i++) {
		bs.push(new Buttons(i));
		bs[i].init();
		for (var j = 0; j < bs[i].parties.length; j++) {
			var b = new Button(j, i, bs[i].name, bs[i].parties[j], false);
			b.init();
		}
	}
}


