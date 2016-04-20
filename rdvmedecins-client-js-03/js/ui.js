//la couche [présentation]
var ui = {
// variables globales;
  "agenda": "",
  "resa": "",
  "langue": "",
  "urlService": "http://localhost:8081",
  "page": "login",
  "jourAgenda": "",
  "idMedecin": "",
  "user": {},
  "login": {},
  "exceptionTitle": {},
  "calendar_infos": {},
  "erreur": "",
  "idCreneau": "",
  "done": "",
// composants de la vue
  "body": "",
  "navbar": "",
  "jumbotron": "",
  "content": "",
  "exception": "",
  "exception_text": "",
  "exception_title": "",
  "loading": ""
};
// la couche des evts
var evts = {};
// la couche [dao]
var dao = {};

// ------------ document ready
$(document).ready(function () {
  // initialisation document
  console.log("document.ready");
  // composants de la page
  ui.navbar = $("#navbar");
  ui.jumbotron = $("#jumbotron");
  ui.content = $("#content");
  ui.erreur = $("#erreur");
  ui.exception = $("#exception");
  ui.exception_text = $("#exception-text");
  ui.exception_title = $("#exception-title");
  // on mémorise la page de login pour pouvoir la restituer
  ui.login.lang = ui.langue;
  ui.login.navbar = ui.navbar.html();
  ui.login.jumbotron = ui.jumbotron.html();
  ui.login.content = ui.content.html();
  // URL du service
  $("#urlService").val(ui.urlService);
});

// ------------------------ fonctions d'initialisation des vues Bootstrap
ui.initNavBarStart = function () {
  // dropdown des langues
  $('.dropdown-toggle').dropdown();
  // l'image animée
  ui.loading = $("#loading");
  ui.loading.hide();
};

ui.initNavBarRun = function () {
  // dropdown des langues
  $('.dropdown-toggle').dropdown();
  // l'image animée
  ui.loading = $("#loading");
  ui.loading.hide();
};

ui.initChoixMedecinJour = function () {
  // calendrier
  var calendar_container = $("#calendar_container");
  ui.calendar_infos = {
    "container": calendar_container,
    "html": calendar_container.html(),
    "today": moment().format('YYYY-MM-DD'),
    "langue": ui.langue
  };
  // création calendrier
  ui.updateCalendar();
  // le select des médecins
  var idMed = $('#idMedecin');
  idMed.selectpicker();
  // premier médecin sélectionné
  ui.idMedecin = $('#idMedecin option').val();
  idMed.change(function () {
    ui.idMedecin = $('#idMedecin option:selected').val();
    evts.getAgenda();
  })
};

ui.updateCalendar = function (renew) {
  if (renew) {
    // régénération du calendrier actuel
    ui.calendar_infos.container.html(ui.calendar_infos.html);
  }
  // initialisation du calendrier
  var calendar = $("#calendar");
  var settings = {
    format: "yyyy-mm-dd",
    startDate: ui.calendar_infos.today,
    language: ui.calendar_infos.langue
  };
  calendar.datepicker(settings);
  // sélection de la date courante
  if (ui.jourAgenda) {
    calendar.datepicker('setDate', ui.jourAgenda)
  }
  // évts
  calendar.datepicker().on('hide', function () {
    // affichage jour sélectionné
    ui.displayJour();
  });
  calendar.datepicker().on('changeDate', function () {
    // on note la nouvelle date
    ui.jourAgenda = moment(calendar.datepicker('getDate')).format("YYYY-MM-DD");
    // affichage infos agenda
    evts.getAgenda();
    // affichage jour sélectionné
    ui.displayJour();
  });
  // affichage jour sélectionné
  ui.displayJour();
};

// affiche le jour sélectionné
ui.displayJour = function () {
  if (ui.jourAgenda) {
    var displayjour = $("#displayjour");
    moment.locale(ui.calendar_infos.langue);
    var jour = moment(ui.jourAgenda).format('LL');
    displayjour.val(jour);
  }
};

ui.initAgenda = function () {
  // le tableau des créneaux horaires
  $("#creneaux").footable();
};

ui.initResa = function () {
  // le select des clients
  $('#idClient').selectpicker();
};


