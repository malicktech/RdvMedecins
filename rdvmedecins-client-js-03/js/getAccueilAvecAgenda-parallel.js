// -------------------------- getAccueilAvecAgenda
evts.getAccueilAvecAgenda=function(ui) {
  // actions [navbar-run, jumbotron, accueil, agenda] en //
  // navbar-run
  var navbarRun = {
    "name": "navbar-run"
  };
  navbarRun.post = {
    "lang": ui.langue
  };
  navbarRun.sendMeBack = {
    "caller": evts.showResult
  };
  // jumbotron
  var jumbotron = {
    "name": "jumbotron"
  };
  jumbotron.post = {
    "lang": ui.langue
  };
  jumbotron.sendMeBack = {
    "caller": evts.showResult
  };
  // accueil
  var accueil = {
    "name": "accueil"
  };
  accueil.post = {
    "lang": ui.langue,
    "user": ui.user
  };
  accueil.sendMeBack = {
    "caller": evts.showResult
  };
  // agenda
  var agenda = {
    "name": "agenda"
  };
  agenda.post = {
    "user": ui.user,
    "lang": ui.langue,
    "idMedecin": ui.idMedecin,
    "jour": ui.jourAgenda
  };
  agenda.sendMeBack = {
    'idMedecin': ui.idMedecin,
    'jour': ui.jourAgenda,
    "caller": evts.getAgendaDone
  };
  // exécution actions en //
  evts.execute([navbarRun, jumbotron, accueil, agenda])
};
