//@author: aguler

var charts = [];

var COLORS = [];
COLORS["V"] = "rgba(196,20,30,1)";
COLORS["S"] = "rgba(239,27,39,0.6)"
COLORS["MP"] = "rgba(139, 180, 40, 1)"
COLORS["C"] = "rgba(66, 184, 123, 1)"
COLORS["FP"] = "rgba(100,201,247,1)"
COLORS["KD"] = "rgba(54,117,200,1)"
COLORS["M"] = "rgba(0, 191, 255,1)";
COLORS["SD"] = "rgba(236,200,0,1)"

function Graph(contname, chartname, category) {
  this.graph;
  this.cat = category;
  this.contname = contname;
  this.max = 0;
  this.name = chartname;
  this.plotlines = [];
  this.jsob = null;
  getURL();
  //default call
  makeCorsReq(this, getURL());

  //search calls makecorsreq with url which calls initObj which in turn calls
  //getparties which checks with parties were clicked in the last plotlines and
  //returns these parties as an array. Initobj then addplotlines for all these
  //parties before finally calling updateGraph.
}

Graph.prototype = {

  initObj: function(resp) {
    this.jsob = JSON.parse(JSON.stringify(resp));
    this.initGraph();
  },
  getSeriesColors: function() {
    var arr = [];
    for (var i = 0; i < this.plotlines.length; i++) {
      var p = this.plotlines[i].party;
      arr.push({color: COLORS[p]});
    }
    return arr;
  },
  //only for search.html
  updateSubject: function(to, from, category) {
    console.log("Category is: " + category);
    makeCorsReq(this, getURLSearch(to, from, category));
  },

  getMax: function() {
    var max = 0;
    for (var i = 0; i < this.jsob.datasets.length; i++) {
      for (var j = 0; j < this.jsob.datasets[i].data.length; j++) {
        var next = this.jsob.datasets[i].data[j].data;
        if (next > max) {
          max = next;
        }
      }
    } 
    return max;
  },

  getPartyObj: function(party) {
    for (var i=0; i < this.jsob.datasets.length; i++) {
      if (party === this.jsob.datasets[i].party) {
        return this.jsob.datasets[i];
      }
    }
    console.error("Party: " + party + " does not exist in database.");
    return null;
  },

  addPlotLine: function(party) {
    var dat = [];
    var obj = this.getPartyObj(party);
    for (var i = 0; i < obj.data.length; i++) {
      dat[i] = obj.data[i].data;
    }
    this.plotlines.push({party: party, data: dat});

    this.updateGraph();
  },

  destroyPlotLine: function(party) {
    for (var i = 0; i < this.plotlines.length; i++) {
      var p = this.plotlines[i].party;
      if (p == party) {
        this.plotlines.splice(i,1);
        break;
      }
    } 
    this.updateGraph();  
  },

  getData: function() {
    var data = [];
    for (var i = 0; i < this.plotlines.length; i++) {
        var l = this.jsob.labels[i];
        console.log(l);
        data.push(this.plotlines[i].data);
    }
    if (data.length == 0) {
        data.push([null]);    
    }
    return data;   
  },

  initGraph: function() {
      this.max = this.getMax();
      //Better if H2 is added as a child to this.contname with cat as attribute
      
      //Ugly code only for version 1
      if (this.cat === null) {
        $("."+this.contname + " h2").text(this.jsob.category);
      }else {
        $("."+this.contname + " h2").text(this.cat);

      }
      //$("."+this.contname + " h2" ).append(this.getObj().category);
      this.updateGraph();
  },

  updateGraph: function() {

    var theme = {
      grid: {
        background: "white"
      },
      series: this.getSeriesColors(),
      axes: {
        xaxis: {
          label: "Tid",
          pad: 0,
        },
        yaxis: {
          label: "",
          tickInterval: 5,
          min: 0,
          max: this.max,
        }
      },
      highlighter: {
        show: true,
        sizeAdjust: 7.5
      }
    }

    if (typeof this.graph === 'object') {
      this.graph.destroy();
      this.graph = $.jqplot (this.name, this.getData(), theme);
      return;
    }
    this.graph = $.jqplot (this.name, this.getData(), theme);
  }

}

function add(chartid, party) {
  for (var i = 0; i < charts.length; i++) {
    if (charts[i].name == chartid) {
      charts[i].addPlotLine(party);
    }
  }
}

function destroy(chartid, party) {
  for (var i = 0; i < charts.length; i++) {
    if (charts[i].name == chartid) {
      charts[i].destroyPlotLine(party);
    }
  }
}



function graphHomeInit() {

  //do searchreq before making graph objects and send each object as a parameter
  //for index.html
  //for search.html make a trending request with 1 as amount and send it as default
  //object

  charts.push(new Graph("chartcont0", "chart0", "Skatt"));  
  charts.push(new Graph("chartcont1", "chart1", "Miljö"));     
  charts.push(new Graph("chartcont2", "chart2", "Försvar"));  

}

function graphSearchInit() {
    charts.push(new Graph("chartcont0", "chart0", null));  
}
