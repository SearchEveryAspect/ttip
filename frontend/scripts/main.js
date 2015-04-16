
function isIndex(){
    var pagePath= window.location.pathname;
    if (pagePath.substring(pagePath.lastIndexOf("/") + 1) == "index.html") {
    	return true;
    }
    return false;

}

function getChartLen() {
  var len = 0;
  while ($(".chartcont" + len).length) {
    len = len + 1;
  }
  return len;
}

function main() {
  searchInit();
  buttonsInit();
}

$(document).ready(main);