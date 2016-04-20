// données globales
var url;
var posted;
var response;
var method;

function requestServer() {
	// on récupère les informations
	var urlValue = url.val();
	var postedValue = posted.val();
	method = document.forms[0].elements['method'].value;
	// on efface la réponse précédente
	response.text("");
	// on fait un appel Ajax à la main
	if (method === "get") {
		doGet(urlValue);
	} else {
		doPost(urlValue, postedValue);
	}
}

function doGet(url) {
	// on fait un appel Ajax à la main
	$.ajax({
		headers : {
			'Authorization' : 'Basic YWRtaW46YWRtaW4='
		},
		url : 'http://localhost:8080' + url,
		type : 'GET',
		dataType : 'tex/plain',
		beforeSend : function() {
		},
		success : function(data) {
			// résultat texte
			response.text(data);
		},
		complete : function() {
		},
		error : function(jqXHR) {
			// erreur système
			response.text(jqXHR.responseText);
		}
	})
}

function doPost(url, posted) {
	// on fait un appel Ajax à la main
	$.ajax({
		headers : {
			'Authorization' : 'Basic YWRtaW46YWRtaW4='
		},
		url : 'http://localhost:8080' + url,
		type : 'POST',
		contentType : 'application/json',
		data : posted,
		dataType : 'tex/plain',
		beforeSend : function() {
		},
		success : function(data) {
			// résultat texte
			response.text(data);
		},
		complete : function() {
		},
		error : function(jqXHR) {
			// erreur système
			response.text(jqXHR.responseText);
		}
	})
}

// au chargement du document
$(document).ready(function() {
	// on récupère les références des composants de la page
	url = $("#url");
	posted = $("#posted");
	response = $("#response");
});
