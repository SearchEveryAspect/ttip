//@author: aguler


var BUTTONS_URL = "url(../img/partiikoner.png)";
var BUTTON_WIDTH = 40;
var BUTTON_HEIGHT = 40;
var PARTIES = [".V",".S", ".MP", ".C", ".FP",".KD",".M", ".SD"];


function Buttons(chartid) {
	this.name = ".chartcont" + chartid + " .buttons";
	this.htmllines = "";
	for (var i = 0; i < PARTIES.length; i++) {
		this.htmllines = this.htmllines + "<li class=" + PARTIES[i].substring(1) + "></li>";
	}
}

/*Add children to class buttons and add css properties to every child. */

Buttons.prototype.init = function() {
	//add html classes
	$(this.name).append(this.htmllines);

	//add css properties
	var x = 0;
	var y = 0;
	for (var i = 0; i < PARTIES.length; i++) {
		var style = {"background": BUTTONS_URL + x + "px" + " " + y + "px"};
		$(this.name + " " + PARTIES[i]).css(style);
		//go down 40 pixles in spritesheet
		y = y - BUTTON_HEIGHT;
	}
}

function Button(id, chartid, bname, name, click) {
	this.name = bname + " " + name;
	this.party = name.substring(1);
	this.chartname = "chart" + chartid;
	this.click = click;
	this.x = 0;
	this.y = -id*BUTTON_HEIGHT;
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
				if (b.click != true) b.update(this, BUTTON_WIDTH);
			},
			click: function() {
				if (b.click == true) {
					b.update(this, 0);
					destroy(b.chartname, b.party);
				}else {
					b.update(this, BUTTON_WIDTH*2);
					add(b.chartname, b.party);
				}
				b.click ^= true;
			}
		});
	},
	update: function(c, xpix) {
		var style = {"background": BUTTONS_URL + (this.x + xpix) + "px" + " " + this.y + "px"};
		$(c).css(style);
	}
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
		for (var j = 0; j < PARTIES.length; j++) {
			var b = new Button(j, i, bs[i].name, PARTIES[j], false);
			b.init();
		}
	}
}


