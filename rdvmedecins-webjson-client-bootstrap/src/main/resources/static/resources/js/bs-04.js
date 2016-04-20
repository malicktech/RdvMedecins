function deconnecter() {
	showInfo("Déconnexion demandée...");
}

function setLang(lang) {
	var msg;
	switch (lang) {
	case 'fr':
		msg = "Vous avez choisi la langue française...";
		break;
	case 'en':
		msg = "You have selected english language...";
		break;
	}
	showInfo(msg);
}

function showInfo(message) {
	$("#info").text(message);
}

function initNavBar3() {
	// dropdown des langues
	$('.dropdown-toggle').dropdown();
	// l'image animée
	loading = $("#loading");
	loading.hide();
}

function afficherAgenda() {
	showInfo("option [Agenda] cliquée...");
}

function retourAccueil() {
	showInfo("option [Retour accueil] cliquée...");
}

function retourAgenda() {
	showInfo("option [Retour agenda] cliquée...");
}

function validerRv() {
	showInfo("option [Valider] cliquée...");
}

function setMenu(show) {
	// les liens du menu
	var lnkAfficherAgenda = $("#lnkAfficherAgenda");
	var lnkAccueil = $("#lnkAccueil");
	var lnkValiderRv = $("#lnkValiderRv");
	var lnkRetourAgenda = $("#lnkRetourAgenda");
	// on les met dans un dictionnaire
	var options = {
		"lnkAccueil" : lnkAccueil,
		"lnkAfficherAgenda" : lnkAfficherAgenda,
		"lnkValiderRv" : lnkValiderRv,
		"lnkRetourAgenda" : lnkRetourAgenda
	}
	// on cache tous les liens
	for ( var key in options) {
		options[key].hide();
	}
	// on affiche ceux qui sont demandés
	for (var i = 0; i < show.length; i++) {
		var option = show[i];
		options[option].show();
	}
}
