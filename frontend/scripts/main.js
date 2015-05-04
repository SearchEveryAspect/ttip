
function isIndex(){
    var pagePath = window.location.pathname;
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
  if (isIndex()) {
    graphHomeInit();
  }else {
    graphSearchInit();
  searchInit();

  }
  buttonsInit();
  navInit();

}

$(document).ready(main);