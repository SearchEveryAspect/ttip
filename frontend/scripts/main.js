function isIndex(){
    var pagePath = window.location.pathname;
    if (pagePath.substring(pagePath.lastIndexOf("/") + 1) == "search") {
    	return false;
    }
    return true;
}

function getChartLen() {
  var len = 0;
  while ($(".chartcont" + len).length) {
    len = len + 1;
  }
  return len;
}

function createHome(resp) {
  var jsob = JSON.parse(JSON.stringify(resp));
  var jsobarr = [];
  for (var i = 0; i < jsob.topTrends.length; i++) {
    jsobarr[i] = jsob.topTrends[i];
  }
  graphInit(jsobarr);
  buttonsInit(getInteresting(jsobarr));
  navInit(jsobarr);
  infoTextInit();

}

function createSearch(resp) {
  var jsob = JSON.parse(JSON.stringify(resp));
  graphInit(jsob.topTrends);
  searchInit();
  buttonsInit(getInteresting(jsob.topTrends));
  infoTextInit();
}

function getInteresting(jsobarr) {
  var arr = [];
  var arrs = [];
  for (var i = 0; i < jsobarr.length; i++) {
    for (var j = 0; j < PARTIES.length; j++) {
      if (jsobarr[i].datasets[j].isIntersting === true) {
        arr.push(jsobarr[i].datasets[j].party);
      }
    }
    arrs.push(sortParties(arr));
    arr = [];
  }
  return arrs;
}

function sortParties(a) {
  var par = PARTIES;
  var arr = []; 
  for (var i = 0; i < PARTIES.length; i++) {
    for (var j = 0; j < a.length; j++) {
      if (a[j] === PARTIES[i]) {
        arr.push(a[j]);
      }
    }
  }
  return arr;
}

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

function main() {
  if (isIndex()) {
    makeHTTPReq(getURLInteresting("3"), createHome);
  //search
  }else {
    makeHTTPReq(getURL("categories"),setSubjects);
    makeHTTPReq(getURLInteresting("1"),createSearch);
  }
}
$(document).ready(main);