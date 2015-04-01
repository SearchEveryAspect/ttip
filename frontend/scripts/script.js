//@author: Alan Güler
//@date: 2015-03-25

//Testing some very basic hard coded graphs


var graph, graph2; 
var plot;
mod = [3,7,9,1,4,6,8,2,5];
vp = [5,2,9,7,0,6,4,1,9];

var plot1 = 
{
  title: 'Skatt',
  grid: {
    background: "white"
  },
  axes: {
    xaxis: {

      label: "",
      pad: 0
    },
    yaxis: {
      label: "",
      min: 0,
      max: 15,
    }
  },
  highlighter: {
    show: true,
    sizeAdjust: 7.5
  }
}

var plot2 = 
{
  title: 'Sjukvård',
  grid: {
    background: "white"
  },
  axes: {
    xaxis: {
      label: "",
      pad: 0
    },
    yaxis: {
      label: "",
      min: 0,
      max: 15,
    }
  },
    highlighter: {
    show: true,
    sizeAdjust: 7.5
  }
}




function init() {
    plots = [mod];
    graph = $.jqplot ('chart1', plots,plot1);
    graph2 = $.jqplot ('chart2', [vp],plot2);

}

function update() {
    graph.destroy();
    graph = $.jqplot ('chart1', plots,plot1);


}

function add() {
    for (var i = 0; i < plots.length; i++) {
      if (plots[i] == vp) {
        plots.pop();
        update();
        return;
      }
    }
    plots.push(vp);
    update();
}