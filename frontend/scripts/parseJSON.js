//@author aguler


//create xmlhttpreq object
function makeCorsReq(c, u) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: u,
    async: true,
    success: function(resp){
      console.log("Response from " + URL + " OK!");
      if (isJSON(JSON.stringify(resp))) {
        c.initObj(resp);
      } else {
        console.error("Not JSON-data");
      }
    },
    error: function(xhr){
      console.error("Error status: " + xhr.status);
    }
  });
}

function isJSON(str) {
	try {
		JSON.parse(str);
		return true;
	} catch (e) {
		return false;
	}
}


