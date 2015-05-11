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

function Graph(contname, chartname, category,obj) {
  this.graph;
  this.cat = category;
  this.contname = contname;
  this.max = 0;
  this.name = chartname;
  this.plotlines = [];
  this.jsob = obj;
  //default call
  this.initGraph();

  //search calls makecorsreq with url which calls initObj which in turn calls
  //getparties which checks with parties were clicked in the last plotlines and
  //returns these parties as an array. Initobj then addplotlines for all these
  //parties before finally calling updateGraph.
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
  getSeriesColors: function() {
    var arr = [];
    for (var i = 0; i < this.plotlines.length; i++) {
      var p = this.plotlines[i].party;
      arr.push({color: COLORS[p]});
    }
    return arr;
  },
  //only for search.html
  updateSubject: function(from, to, category) {
    //console.log("Category is: " + category, " From: " + from + " To: " + to);
    makeCorsReq(getURLSearch(from,to,category), this.initObj, this);
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
      //console.log("doc length" + obj.data[i].docs.length);
      for (var j = 0; j < obj.data[i].docs.length; j++) {
        links.push({title: obj.data[i].docs[j].title, link: obj.data[i].docs[j].link, date:obj.data[i].docs[j].date});
        //console.log("This is link title: " + obj.data[i].docs[j].title + " and this is the link: " + dat[i].link);
      }
      linkset.push(links);
    }
    this.plotlines.push({party: party, data: dat, linkset: linkset});
    //console.log("Current length of plotlines: " + this.plotlines.length);
    //console.log(this.plotlines[0].linkset[0].length);
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
          label: "Antal motioner",
          tickInterval: this.max/10,
          min: 0,
          max: this.max*1.2,
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
      //console.log("Data: " + data[1] + " seriesIndex: " + seriesIndex + " pointIndex: " + pointIndex + " Time: " + charts[index].jsob.labels[pointIndex]);
      clearAll();
      for (var i = 0; i < charts[index].plotlines.length; i++) {
        addToInfo(charts[index].jsob.labels[pointIndex], index, charts[index].plotlines[i].party, charts[index].plotlines[i].linkset[pointIndex]);
      }
      //console.log(chart.plotlines[j].linkset[].title);
  });
}

function graphHomeInit(objarr) {

  //for search.html make a trending request with 1 as amount and send it as default
  //object
  /**
  for (var i = 0; i < objarr.length; i++) {
    charts.push(new Graph("chartcont" + i, "chart"+ i, "Skatt", objarr[i])); 
    var chart = charts[i];
    $("#" + chart.name).bind('jqplotDataClick',
    function (ev, seriesIndex, pointIndex, data) {
        // data contains the data point value, play with it
        // NOTE it may contain an array and not a string
        console.log("Data: " + data[1] + " seriesIndex: " + seriesIndex + " pointIndex: " + pointIndex + " Time: " + chart.jsob.labels[pointIndex]);
        console.log(i);
    });

  }
  */
  for (var i= 0; i<getChartLen(); i++) {
    charts.push(new Graph("chartcont" + i, "chart" + i, objarr[i].category.capitalize(), objarr[i]));
    handleEvent(i);
  }



}

function graphSearchInit(obj) {
    //console.log("this is category search: " + obj.topTrends[0].category.capitalize());
    charts.push(new Graph("chartcont0", "chart0", obj.topTrends[0].category.capitalize(), obj.topTrends[0]));  
    handleEvent(0);
}

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}



