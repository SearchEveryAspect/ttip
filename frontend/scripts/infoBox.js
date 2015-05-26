var INFO = "Klicka på en datapunk i grafen för att få mer information om tidsperioden och länkar till motioner."
var ICONS_URL = "url(../img/buttons_clicked35.png)";
var PLUS_URL = "url(../img/plus_icon.png)"
var MINUS_URL = "url(../img/minus_icon.png)"


var charttexts= [];

function InfoText(infoname, index) {
	this.index = index;
	this.divName = "."+infoname+index;
	this.info = $(this.divName);
	this.parties = [];
	this.mouseEvents = [];
}

InfoText.prototype = {
	init: function() {
		$(this.divName + " #help").text(INFO);
		var y = 0;	
		for (var i = 0; i < PARTIES.length; i++) {
			this.getElement(PARTIES[i], "party").css({"background": ICONS_URL + "0px " + y + "px", "display": "none"});
			this.getElement(PARTIES[i], "max-min").css({"background": PLUS_URL, "display": "none"});
			y = y - 35;
			this.mouseEvents[i] = new mouseEventHandler(this.index, PARTIES[i]);
			this.mouseEvents[i].status();
		}
	},
	getElement: function() {
		var elem = this.info;
		for (var i =0; i < arguments.length; i++) {
			if(document.getElementById(arguments[i])) {
				arguments[i] = "#"+arguments[i];
			}else {
				if (document.getElementsByClassName(arguments[i]).length > 0) {
					arguments[i] = "."+arguments[i];
				} 
			}
			elem = elem.find(arguments[i]);

		}
 		return elem;
	},
	getLinks: function(party) {
		for (var i = 0; i < this.parties.length; i++) {
			if (this.parties[i].party === party) {
				return this.parties[i].links;
			}
		}
		console.error("Party " + p + " does not exist.");
		return null;
	},
	add: function (time, party, linkarr) {
		if (linkarr.length < 1) {
			return;
		}
		this.parties.push({party: party, links: linkarr});
		this.getElement("time-period").text("Tidpunkt: " + time);
		this.getElement(party).css({"display":""});
		var mot = " motion";
		if (linkarr.length > 1) mot += "er";
		this.getElement(party, "amount").append(linkarr.length + mot);
		this.getElement(party, "party").css({"display":""});
		this.getElement(party,"max-min").css({"display":""});

	},
	clearAll: function() {
		for (var i = 0; i < this.parties.length; i++) {
			this.getElement(this.parties[i].party).css({"display":"none"});
			this.getElement(this.parties[i].party, "amount").text("");
			this.getElement(this.parties[i].party+"motions").remove();
			this.getElement(this.parties[i].party, "max-min").css({"background":PLUS_URL});
		}
		for (var j = 0; j < PARTIES.length; j++ ) {
			this.mouseEvents[j].setClick(false);
			this.getElement(PARTIES[j], "max-min").trigger("mouseleave");
		}
		this.parties=[];
	}
	
}

function infoTextInit() {
	for (var t = 0; t < getChartLen(); t++) {
		charttexts[t] = new InfoText("charttext", t);
		charttexts[t].init();
	}
}

function mouseEventHandler(index, party) {
	this.index = index;
	this.party = party;
	this.clicked = false;
}

mouseEventHandler.prototype = {
	status: function() {
		var c = this;
		var elem = charttexts[this.index].getElement(this.party, "max-min");
		elem.bind("click", function() {
			elem.toggle();
			if (c.clicked) {
				elem.css({"background": PLUS_URL});
				charttexts[c.index].getElement(c.party+"motions").remove();
			} else {
				elem.css({"background": MINUS_URL});
				var links = charttexts[c.index].getLinks(c.party);
				for (var j = 0; j < links.length; j++) {
	            	charttexts[c.index].getElement(c.party).after("<tr class="+c.party+"motions><td class=date><p>"+
	            		links[links.length-1-j].date+"</p></td><td colspan=2><a href="+
	            			links[links.length-1-j].link+" target=_blank><p>"+links[links.length-1-j].title.shorten()+
	            				"</p></a></td></tr>");
				}																
			}
			elem.toggle();
			c.clicked ^=true;

		});
		elem.on("mouseenter", function() {
			elem.css({"opacity": "1"});
		});
		elem.on("mouseleave", function() {
			if (c.clicked!=true) elem.css({"opacity": "0.5"});

		});

		$("#btn").on("click", function() {
			console.log("Came here!");
			if(c.clicked) {
				c.clicked = false;
				elem.css({"background": PLUS_URL});
				charttexts[this.index].getElement(party+"motions").remove();
			}
			charttexts[c.index].clearAll();
		});
	},
	setClick: function(bool) {
		this.clicked = bool;
	}
}
String.prototype.shorten = function() {
  var splitStr = this.split(" ");
  for (var i = 0; i < splitStr.length; i++) {
    if (new RegExp(/^[A-ZÅÄÖ]/).test(splitStr[i])) {
      splitStr.splice(0,i);
      break;
    }
  }
  var str = splitStr.join(" ");
  if (str.length <= 100) {
  	return str;
  }
  return str.slice(0,100) + "...";
}


function clearAll(i) {
	charttexts[i].clearAll();
}

function addToInfo(time, index, party, linkarr) {
	charttexts[index].add(time, party, linkarr);
}