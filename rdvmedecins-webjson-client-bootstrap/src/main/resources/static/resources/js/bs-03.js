function initNavBar2() {
	// dropdown des langues
	$('.dropdown-toggle').dropdown();
}

function connecter() {
	showInfo("Connexion demandée...");
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