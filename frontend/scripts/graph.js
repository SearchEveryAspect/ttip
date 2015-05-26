var charts = [];
var COLORS = new Array(8);
COLORS["V"] = "rgba(196,20,30,0.6)";
COLORS["S"] = "rgba(239,27,39,0.5)";
COLORS["MP"] = "rgba(139, 180, 40, 0.6)";
COLORS["C"] = "rgba(66, 184, 123, 0.6)";
COLORS["FP"] = "rgba(100,201,247,0.6)";
COLORS["KD"] = "rgba(54,117,200,0.6)";
COLORS["M"] = "rgba(0, 191, 255,0.6)";
COLORS["SD"] = "rgba(236,200,0,0.6)";

function Graph(index, contname, chartname, category,obj) {
  this.graph;
  this.index = index;
  this.cat = category;
  this.contname = contname;
  this.max = 0;
  this.name = chartname;
  this.plotlines = [];
  this.jsob = obj;
  //default call
  this.initGraph();

}

Graph.prototype = {

  initObj: function(resp, c) {
    c.jsob = JSON.parse(JSON.stringify(resp));
    c.cat = c.jsob.category.capitalize();
    var par = c.getParties();
    for (var i = 0; i < par.length; i++) {
      c.destroyPlotLine(par[i]);
      c.addPlotLine(par[i]);
    }
    c.initGraph(); 
  },
  getParties: function() {
    var arr = [];
    for (var i = 0; i < this.plotlines.length; i++) {
      arr.push(this.plotlines[i].party);
    }
    return arr;
  },
  getSeries: function() {
    var arr = [];
    for (var i = 0; i < this.plotlines.length; i++) {
      var p = this.plotlines[i].party;
      arr.push({label: p, showLabel: true, shadow: true, lineWidth: 5, color: COLORS[p]});
    }
    return arr;
  },
  updateSubject: function(from, to, category) {
    makeHTTPReq(getURLSearch(from,to,category), this.initObj, this);
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
    var links = [];
    var linkset = [];
    var obj = this.getPartyObj(party);
    //console.log(obj.data.length);
    for (var i = 0; i < obj.data.length; i++) {
      var links = [];
      dat.push(obj.data[i].data);
      for (var j = 0; j < obj.data[i].docs.length; j++) {
        links.push({title: obj.data[i].docs[j].title, link: obj.data[i].docs[j].link, date:obj.data[i].docs[j].date});
      }
      linkset.push(links);
    }
    this.plotlines.push({party: party, data: dat, linkset: linkset});
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

  getPartyData: function(d) {
    var arr = [];
    for (var i = 0; i < d.length; i++) {
      arr.push([this.jsob.labels[i],d[i]]);
    }
    return arr;  
  },

  getData: function() {
    var data = [];
    for (var i = 0; i < this.plotlines.length; i++) {
      var l = this.jsob.labels[i];
      data.push(this.plotlines[i].data);
    }
    if (data.length == 0) {
        data.push([null]);    
    }
    return data;   
  },
  initGraph: function() {
      this.max = this.getMax();
      $("."+this.contname + " h2").text(this.cat);
      this.updateGraph();
  },

  getPeriod: function() {
    var monthMS = 2629743830; //month in ms
    var scale = 6;
    var interval = Date.parse(this.jsob.labels[this.jsob.labels.length-1]) - Date.parse(this.jsob.labels[0]);
    var label = " months";
    var d = interval / (monthMS * scale);
    if (d < 1) {
      return "1 month";
    }
    d = Math.ceil(d);
    return d + label;
  },

  getYInterval: function() {
    var i = Math.ceil(this.max/10);
    if (i < 1) {
      return 1;
    } else {
      return i;
    }
  },

  updateGraph: function() {

    var d = Date.parse(this.jsob.labels[0]) + (Date.parse(this.getPeriod()) * (this.jsob.length -2));
    var theme = {
      cursor: {
        show: true,
        zoom: true,
        showTooltip: true
      },
      animate: false,
      animateReplot: false,
      grid: {
        background: "white",
      },
      series: this.getSeries(),
      axes: {
        xaxis: {
          renderer: $.jqplot.DateAxisRenderer,
          label: "Tid",
          min: this.jsob.labels[0],
          max: this.jsob.labels[this.jsob.labels.length-1],
          tickInterval: this.getPeriod(),
        },
        yaxis: {
          label: "Antal motioner",
          labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
          labelOptions: {
            fontSize: '14pt'
          },
          tickInterval: this.getYInterval(),
          min: 0,
          max: this.max,
        }
      },
      highlighter: {
        show: true,
        sizeAdjust: 15
      },
      legend: {
        show:false
      }
    }

    var arr = [];
    var arrs = [];
    var d = this.getData();
    for (var i = 0; i < d.length; i++) {
      for (var j = 0; j < d[i].length; j++) {
        arr.push([this.jsob.labels[j], d[i][j]]);
      }
      arrs.push(arr);
      arr = [];
    }
    if (typeof this.graph === 'object') {
      theme.animate = false;
      theme.animateReplot = true;
      this.graph.destroy();
      this.graph = $.jqplot (this.name, arrs, theme);
      return;
    }
    this.graph = $.jqplot (this.name, arrs, theme);
  }

}

function add(chartid, party) {
  for (var i = 0; i < charts.length; i++) {
    if (charts[i].name == chartid) {
      if (charts[i].jsob == null) {
        return null;
      }
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

function handleEvent(index) {
  $("#" + charts[index].name).bind('jqplotDataClick',
    function (ev, seriesIndex, pointIndex, data) {
      clearAll(index);
      for (var i = 0; i < charts[index].plotlines.length; i++) {
        addToInfo(charts[index].jsob.labels[pointIndex], index, charts[index].plotlines[i].party, charts[index].plotlines[i].linkset[pointIndex]);
      }
  });
}

function graphInit(objarr) {
  for (var i= 0; i<getChartLen(); i++) {
    charts.push(new Graph(i, "chartcont" + i, "chart" + i, objarr[i].category.capitalize(), objarr[i]));
    handleEvent(i);
  }
}




