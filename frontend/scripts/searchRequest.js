//@author: aguler


function searchRequest(b) {
  this.bajs = b.bajs;
  this.portnr = b.portnr;
  this.type = b.type;
  this.category = b.category;
  this.label = b.label;
  this.from = b.from;
  this.to = b.to;
}

searchRequest.prototype = {
  getReq: function() {
    var vars = "";
    for (var name in this) {
      if (typeof(this[name])==='string') {
        vars += this[name] + "/";
      }
    }

    return vars.substring(0, vars.length -1);
   }
}

function Builder() {
  this.bajs;
  this.portnr;
  this.type;
  this.category;
  this.label;
  this.from;
  this.to;

}

Builder.prototype = {
  portnr: function(nr) {
    this.portnr = "http://localhost:" + nr;
    return this;
  },
  bajs: function(b) {
    this.bajs = b;
    return this;
  },
  type: function(t) {
    this.type = t;
    return this;
  },
  category: function(c) {
    this.category = c;
    return this;

  },
  label: function(l) {
    this.label = l;
    return this;
  },
  from: function(f) {
    this.from = "from/" + f;
    return this;
  },
  to: function(t) {
    this.to = "to/" + t;
    return this;
  }
}
//Default
function getURL() {
    return new searchRequest(new Builder().portnr("8080").type("mot").category("Skatt").label("month").from("2015-03-28").to("2014-04-01")).getReq();
}

function getURLSearch(from, to, category) {
    var s = new searchRequest(new Builder().portnr("8080").type("mot").category(category).label("month").from(from).to(to)).getReq();
    return s;
}



