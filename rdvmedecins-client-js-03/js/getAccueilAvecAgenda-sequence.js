// -------------------------- getAccueilAvecAgenda
evts.getAccueilAvecAgenda=function(ui) {
  // actions [navbar-run, jumbotron, accueil, agenda] dans l'ordre
  // agenda
  var agenda = {
    "name" : "agenda"
  };
  agenda.post = {
    "user" : ui.user,
    "lang" : ui.langue,
    "idMedecin" : ui.idMedecin,
    "jour" : ui.jourAgenda
  };
  agenda.sendMeBack = {
    'idMedecin' : ui.idMedecin,
    'jour' : ui.jourAgenda,
    "caller" : evts.getAgendaDone
  };
  // accueil
  var accueil = {
    "name" : "accueil"
  };
  accueil.post = {
    "lang" : ui.langue,
    "user" : ui.user
  };
  accueil.sendMeBack = {
    "caller" : evts.showResult,
    "next" : agenda
  };
  // jumbotron
  var jumbotron = {
    "name" : "jumbotron"
  };
  jumbotron.post = {
    "lang" : ui.langue
  };
  jumbotron.sendMeBack = {
    "caller" : evts.showResult,
    "next" : accueil
  };
  // navbar-run
  var navbarRun = {
    "name" : "navbar-run"
  };
  navbarRun.post = {
    "lang" : ui.langue
  };
  navbarRun.sendMeBack = {
    "caller" : evts.showResult,
    "next" : jumbotron
  };
  // exécution actions en séquence
  evts.execute([ navbarRun ])
};
