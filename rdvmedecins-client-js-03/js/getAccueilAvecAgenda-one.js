// -------------------------- getAccueilAvecAgenda
evts.getAccueilAvecAgenda=function(ui) {
  // paramètres requête
  var post = {
    "user": ui.user,
    "lang": ui.langue,
    "idMedecin": ui.idMedecin,
    "jour": ui.jourAgenda
  };
  var sendMeBack = {
    "caller": evts.getAccueilAvecAgendaDone
  };
  // requête
  evts.execute([{
    "name": "accueil-avec-agenda",
    "post": post,
    "sendMeBack": sendMeBack
  }]);
};
