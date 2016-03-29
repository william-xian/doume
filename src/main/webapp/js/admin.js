/**
 * /home/william/workspace/doume/src/main/webapp/WEB-INF/jsp/admin/admin.jsp
 */
(function($) {
    $.setObj = function(k, v) {
	if (v) {
	    var jsonstring = JSON.stringify(v);
	    sessionStorage.setItem(k, jsonstring);
	} else {
	    sessionStorage.removeItem(k);
	}
    }
    $.getObj = function(k) {
	var jsonstring = sessionStorage.getItem(k);
	return JSON.parse(jsonstring);
    }
})(jQuery);

var urlHOME = "http://localhost:8080/doume/admin/";
$(document).ready(function() {
    //init();
});

function init() {
    var usr = $.getObj("usr");
    if (usr == null) {
	window.setTimeout(function() {
	    $.mobile.changePage("#login");
	}, 500);
    }
}
function actionFormLogin() {
    var urlparam = urlHOME + "login.json?" + $(this).serialize();
    jQuery.post(urlparam, function(data) {
	if (data.OM != "ERROR") {
	    $.setObj("usr", data.OM);
	    $.mobile.changePage("#admin-home");
	} else {
	    alert("Error!");
	}
    });
    return false;
}

