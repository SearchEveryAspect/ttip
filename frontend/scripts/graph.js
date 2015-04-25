//@author: aguler

var charts = [];

var COLORS = [];
COLORS["vp"] = "rgba(196,20,30,1)";
COLORS["s"] = "rgba(239,27,39,1)"
COLORS["mp"] = "rgba(139, 200, 83, 1)"
COLORS["c"] = "rgba(66, 184, 123, 1)"
COLORS["fp"] = "rgba(93,201,247,1)"
COLORS["kd"] = "rgba(54,117,171,1)"
COLORS["m"] = "rgba(0, 191, 255,1)";
COLORS["sd"] = "rgba(236,200,0,1)"

function Graph(contname, chartname, category) {
  this.graph;
  this.cat = category;
  this.contname = contname;
  this.max = 0;
  this.name = chartname;
  this.plotlines = [];
  this.initGraph();
}

Graph.prototype = {
  getObj: function() {
    // index.html should call parseJSON to get jsobj.
    // search.html should have a mutator method to get jsobj from eventhandler in search.js. 
    // For now use this object.
    var jsob = {
      category: "Skatt",

      labels: ["2014-11-01", "2014-12-01", "2015-01-01","2015-02-01","2015-03-01"],
      dataset: [
        {party: "vp", data: [14,17,1,11,8]},
        {party: "s", data: [3,4,15,9,10]},
        {party: "mp", data: [16,11,7,16,19]},
        {party: "c", data: [20,3,16,5,15]},
        {party: "fp", data: [13,9,4,6,4]},
        {party: "kd", data: [13,1,12,16,10]},
        {party: "m", data: [5,1,9,12,4]},
        {party: "sd", data: [4,8,12,7,4]},
      ]
    };
    return jsob;
  },
 

  getSeriesColors: function() {
    var arr = [];
    for (var i = 0; i < this.plotlines.length; i++) {
      var p = this.plotlines[i].party;
      arr.push({color: COLORS[p]});
    }
    return arr;
  },

  getMax: function() {
    var max = 0;
    var obj = this.getObj();
    for (var i = 0; i < obj.dataset.length;i++) {
      var next = Math.max.apply(null, obj.dataset[i].data);
      if (next > max) {
        max = next;
      }
    } 
    return max;
  },

  addPlotLine: function(party) {
    for (var i = 0; i < this.getObj().dataset.length; i++) {
      var p = this.getObj().dataset[i].party;
      var d = this.getObj().dataset[i].data;
      if (p == party) {
        this.plotlines.push({party: p, data: d});
        break;
      }
    }
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
        data.push(this.plotlines[i].data);
    }
    if (data.length == 0) {
        data.push([null]);
      
    }
    return data;   
  },

  initGraph: function() {
      this.getMax();
      $("."+this.contname + " h2").append(this.cat);
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
          max: this.getMax(),
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
  var j = 0;
  for (var i = 0; i < charts.length; i++) {
    if (charts[i].name == chartid) {
      charts[i].addPlotLine(party);
      var j = j + 1;
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



function homeinit() {

}

function searchInit() {
  /**
  for (var i = 0; i < getChartLen(); i++) {
    charts.push(new Graph("chartcont" + i, "chart" + i));
  } 
  */
  charts.push(new Graph("chartcont0", "chart0", "Skatt"));  
  charts.push(new Graph("chartcont1", "chart1", "Miljö"));     
  charts.push(new Graph("chartcont2", "chart2", "Invandring"));     

}
