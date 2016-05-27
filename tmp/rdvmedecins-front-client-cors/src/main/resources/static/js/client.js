// données globales
var url;
var posted;
var response;
var method;
var baseUrl = 'http://localhost:8080';
var identifiant;
var password;
var authorizationHeader;

function requestServer() {
	// on récupère les informations du formulaire 
	var urlValue = url.val();
	var postedValue = posted.val();
	var identifiantValue = identifiant.val();
	var passwordValue = password.val();
	var method = document.forms[0].elements['method'].value;
	authorizationCode = btoa(identifiantValue + ':' + passwordValue);
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
	// on fait un appel Ajax manuel
	console.log("test");
	$.ajax({
		headers : {
			'Authorization':'Basic '+authorizationCode
		},
		url : baseUrl + url,
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
			response.text(JSON.stringify(jqXHR.statusCode()));
		}
	})
}

function doPost(url, posted) {
	// on fait un appel Ajax à la main
	$.ajax({
		headers : {
			'Authorization':'Basic '+authorizationCode
		},
		url : baseUrl + url,
		type : 'POST',
		contentType : 'application/json; charset=UTF-8',
		data : posted,
		dataType : 'text',
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
			response.text(JSON.stringify(jqXHR.statusCode()));
		}
	})
}

// au chargement du document
$(document).ready(function() {
	// on récupère les références des composants de la page
	identifiant = $("#identifiant");
	password = $("#password");
	url = $("#url");
	posted = $("#posted");
	response = $("#response");
});
