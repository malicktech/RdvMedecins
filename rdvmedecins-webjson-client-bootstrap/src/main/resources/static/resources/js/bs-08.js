function deconnecter() {
	showInfo("Déconnexion demandée...");
}

function setLang(lang) {
	if (lang == calendar_infos.langue) {
		return;
	}
	calendar_infos.langue = lang;
	updateCalendar(true);
	afficherAgenda();
}

function showInfo(message) {
	$("#info").text(message);
}

function initNavBar3() {
	// dropdown des langues
	$('.dropdown-toggle').dropdown();
	// les liens du menu
	var lnkAfficherAgenda = $("#lnkAfficherAgenda");
	var lnkAccueil = $("#lnkAccueil");
	var lnkValiderRv = $("#lnkValiderRv");
	var lnkRetourAgenda = $("#lnkRetourAgenda");
	// on les met dans un tableau
	options = [ lnkAfficherAgenda, lnkAccueil, lnkRetourAgenda, lnkValiderRv ];
	// l'image animée
	loading = $("#loading");
	loading.hide();
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

function afficherAgenda() {
	var idMedecin = $('#idMedecin option:selected').val();
	var jour = moment($('.input-group.date').datepicker('getDate')).format("YYYY-MM-DD")
	if (calendar_infos.date) {
		showInfo("Vous avez sélectionné le médecin d'id=" + idMedecin + " et le jour " + jour);
	}
}

function initChoixMedecinJour() {
	// calendrier
	var calendar_container = $("#calendar_container");
	calendar_infos = {
		"container" : calendar_container,
		"html" : calendar_container.html(),
		"today" : moment().format('YYYY-MM-DD'),
		"langue" : "fr"
	}
	// création calendrier
	updateCalendar();
	// le select des médecins
	$('#idMedecin').selectpicker();
	$('#idMedecin').change(function(e) {
		afficherAgenda();
	})
	// le menu
	setMenu([]);
}

function updateCalendar(renew) {
	if (renew) {
		// régénération du calendrier actuel
		calendar_infos.container.html(calendar_infos.html);
	}
	// initialisation du calendrier
	var calendar = $("#calendar");
	var settings = {
		format : "yyyy-mm-dd",
		startDate : calendar_infos.today,
		language : calendar_infos.langue,
	};
	calendar.datepicker(settings);
	// sélection de la date courante
	if (calendar_infos.date) {
		calendar.datepicker('setDate', calendar_infos.date)
	}
	// évts
	calendar.datepicker().on('hide', function(e) {
		// affichage jour sélectionné
		displayJour();
	});
	calendar.datepicker().on('changeDate', function(e) {
		// on note la nouvelle date
		calendar_infos.date = moment(calendar.datepicker('getDate')).format("YYYY-MM-DD");
		// affichage infos agenda
		afficherAgenda();
		// affichage jour sélectionné
		displayJour();
	});
	// affichage jour sélectionné
	displayJour();
}

// affiche le jour sélectionné
function displayJour() {
	if (calendar_infos.date) {
		var displayjour = $("#displayjour");
		moment.locale(calendar_infos.langue);
		jour = moment(calendar_infos.date).format('LL');
		displayjour.val(jour);
	}
}

function initAgenda() {
	// le tableau des créneaux horaires
	$("#creneaux").footable();
}

var idCreneau;
var idClient;
var resa;

function showDialogResa(idCreneau) {
	// on mémorise l'id du créneau
	this.idCreneau = idCreneau;
	// on affiche le dialogue de réservation
	var resa = $("#resa");
	resa.modal('show');
	// log
	showInfo("Réservation du créneau n° " + idCreneau);
}

function cancelDialogResa() {
	// on cache la boîte de dialogue
	resa.modal('hide');
}

// validation résa
function validateResa() {
	// on récupère les infos
	var idClient = $('#idClient option:selected').val();
	// on cache la boîte de dialogue
	resa.modal('hide');
	// infos
	showInfo("Réservation du créneau n° " + idCreneau + " pour le client n° " + idClient)
}

function supprimer(idRv) {
	showInfo("Suppression du rv n° " + idRv);
}

function initResa() {
	// le select des clients
	$('#idClient').selectpicker();
	// boîte modale
	resa = $("#resa");
	resa.modal({});
}

// ------------ document ready
var calendar_infos = {};

$(document).ready(function() {
	// initialisation document
	console.log("document.ready");
});
