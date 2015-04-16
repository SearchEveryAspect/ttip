/**
@author Alan Guler
@date 2015-04-07
*/

/*This script will call XMLRequest when called and parse JSON received
/*Make this also do request?*/

$(document).ready(function() {

	//com.sea.ttip/prop/cat/month/from/2014-01-01/to/2014-12-31/?party=s,m

    var url= "data.json";
    var xhr = new XMLHttpRequest();
    // false means exercise will wait until it gets a response
    // from server
    xhr.open("GET", url, false);
    xhr.send();
    xhr.onreadystatechange = function() {
   		if (xhr.readyState == 4 && xhr.status == 200) {
	   		var jso = JSON.parse(xhr.responseText);
	   	} else {

	   	}
	}
});

//test validity of json
//TODO also test that the JSON data complies with the BE-FE schema
function isJSON(str) {
	try {
		JSON.parse(str);
		return true;
	} catch (e) {
		console.log("Not JSON-data");
		return false;
	}
}


