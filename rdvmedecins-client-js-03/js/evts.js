// --------------------------- interface logicielle
// exécution d'une suite d'actions
evts.execute = function (actions) {
  // travail en cours ?
  if (evts.travailEnCours) {
    // on ne fait rien
    return;
  }
  // attente
  evts.beginWaiting();
  // exécution des actions
  dao.doActions(actions, evts.stopWaiting);
};

// --------------------------------- implémentation

// début d'attente
evts.beginWaiting = function () {
  // début attente
  ui.loading = $("#loading");
  ui.loading.show();
  ui.exception.hide();
  ui.erreur.hide();
  evts.travailEnCours = true;
};

// fin d'attente
evts.stopWaiting = function () {
  // fin attente
  evts.travailEnCours = false;
  ui.loading = $("#loading");
  ui.loading.hide();
};

// affichage résultat
evts.showResult = function (result) {
  // on affiche les données reçues
  var data = result.data;
  // on analyse le status
  switch (result.status) {
    case 1:
      // erreur ?
      if (data.status == 2) {
        ui.erreur.html(data.content);
        ui.erreur.show();
      } else {
        if (data.navbar) {
          ui.navbar.html(data.navbar);
        }
        if (data.jumbotron) {
          ui.jumbotron.html(data.jumbotron);
        }
        if (data.content) {
          ui.content.html(data.content)
        }
        if (data.agenda) {
          ui.agenda = $("#agenda");
          ui.agenda.html(data.agenda);
          ui.resa = $("#resa");
        }
      }
      break;
    case 2:
      // affichage erreur
      evts.showException(data);
      break;
  }
};

// ------------ fonctions diverses
evts.showException = function (data) {
  // affichage erreur
  ui.exception.show();
  ui.exception_text.html(data);
  ui.exception_title.text(ui.exceptionTitle[ui.langue]);
};

// actions de l'application
// ------------------------ changement de langue
evts.setLang = function (lang) {
  // chgt de langue ?
  if (lang == ui.langue) {
    // on ne fait rien
    return;
  }
  // nouvelle langue
  ui.langue = lang;
  // quelle page faut-il traduire ?
  switch (ui.page) {
    case "login":
      evts.getLogin();
      break;
    case "accueil-sans-agenda":
      evts.getAccueilSansAgenda();
      break;
    case "accueil-avec-agenda":
      evts.getAccueilAvecAgenda(ui);
      break;
  }
};

// les différentes actions
// ---------------- login
evts.getLogin = function () {
  // a-t-on une URL de service ?
  if (ui.page == "login") {
    // on fixe l'URL du serveur
    ui.urlService = $("#urlService").val().trim();
    dao.setUrlService(ui.urlService);
  }
  var post = {
    "lang": ui.langue
  };
  var sendMeBack = {
    "caller": evts.getLoginDone
  };
  evts.execute([{
    "name": "login",
    "post": post,
    "sendMeBack": sendMeBack
  }]);
};

evts.getLoginDone = function (result) {
  // affichage résultat
  evts.showResult(result);
  // nouvelle page ?
  if (result.status == 1 && result.data.status == 1) {
    ui.page = "login";
    $("#urlService").val(ui.urlService);
  }
};

// -------------------------- getAccueilSansAgenda
evts.getAccueilSansAgenda = function () {
  // paramètres requête
  var post = {
    "lang": ui.langue,
    "user": ui.user
  };
  var sendMeBack = {
    "caller": evts.getAccueilSansAgendaDone
  };
  // requête
  evts.execute([{
    "name": "accueil-sans-agenda",
    "post": post,
    "sendMeBack": sendMeBack
  }]);
};

evts.getAccueilSansAgendaDone = function (result) {
  // affichage résultat
  evts.showResult(result);
  // nouvelle page ?
  if (result.status == 1 && result.data.status == 1) {
    ui.page = "accueil-sans-agenda";
  }
};

evts.getAccueilAvecAgendaDone = function (result) {
  // affichage résultat
  evts.showResult(result);
  // nouvelle page ?
  if (result.status == 1 && result.data.status == 1) {
    ui.page = "accueil-avec-agenda";
  }
};
// ---------------- jumbotron
evts.getJumbotron = function () {
  var post = {
    "lang": ui.langue
  };
  var sendMeBack = {
    "caller": evts.showResult
  };
  evts.execute([{
    "name": "jumbotron",
    "post": post,
    "sendMeBack": sendMeBack
  }]);
};

// ---------------- accueil
evts.getAccueil = function () {
  var post = {
    "lang": ui.langue,
    "user": ui.user
  };
  var sendMeBack = {
    "caller": evts.showResult
  };
  evts.execute([{
    "name": "jumbotron",
    "post": post,
    "sendMeBack": sendMeBack
  }]);
};

// ---------------- agenda
evts.getAgenda = function () {
  // qq chose à faire ?
  if (!ui.idMedecin || !ui.jourAgenda) {
    return;
  }
  // on demande la vue [agenda]
  var post = {
    "user": ui.user,
    "lang": ui.langue,
    "idMedecin": ui.idMedecin,
    "jour": ui.jourAgenda
  };
  var sendMeBack = {
    'idMedecin': ui.idMedecin,
    'jour': ui.jourAgenda,
    "caller": evts.getAgendaDone
  };
  // on fait la requête
  evts.execute([{
    "name": "agenda",
    "post": post,
    "sendMeBack": sendMeBack
  }]);
};

evts.getAgendaDone = function (result) {
  // affichage résultats
  evts.showResult(result);
  if (result.status == 1 && result.data.status == 1) {
    // page
    ui.page = "accueil-avec-agenda";
  }
  // mémoire
  var sendMeBack = result.sendMeBack;
  ui.jourAgenda = sendMeBack.jour;
  ui.idMedecin = sendMeBack.idMedecin;
};


// ------------------------ connexion
evts.connecter = function () {
  // on récupère les valeurs à poster
  var login = $("#login").val().trim();
  var passwd = $("#passwd").val().trim();
  // on fixe l'URL du serveur
  ui.urlService = $("#urlService").val().trim();
  dao.setUrlService(ui.urlService);
  // paramètres de la requête
  var post = {
    "user": {
      "login": login,
      "passwd": passwd
    },
    "lang": ui.langue
  };
  var sendMeBack = {
    "user": {
      "login": login,
      "passwd": passwd
    },
    "caller": evts.connecterDone
  };
  // on fait la requête
  evts.execute([{
    "name": "accueil-sans-agenda",
    "post": post,
    "sendMeBack": sendMeBack
  }]);
};

evts.connecterDone = function (result) {
  // affichage résultat
  evts.showResult(result);
  // postAction
  var sendMeBack = result.sendMeBack;
  // connexion réussie ?
  if (result.status == 1 && result.data.status == 1) {
    // page
    ui.page = "accueil-sans-agenda";
    // on note l'utilisateur
    ui.user = sendMeBack.user;
  }
};

// ------------------------ déconnexion
evts.deconnecter = function () {
  // plus d'utilisateur
  ui.user = {};
  if (ui.login.lang == ui.langue) {
    // restitution de la page de login mémorisée
    ui.navbar.html(ui.login.navbar);
    ui.content.html(ui.login.content);
    ui.page = "login";
    // URL du service
    $("#urlService").val(ui.urlService);
    return;
  }
  // il faut redemander la vue dans la nouvelle langue
  evts.getLogin();
};

// ------------------------ suppression d'un RV
evts.supprimerRv = function (idRv) {
  // paramètres de la requête
  var post = {
    "user": ui.user,
    "lang": ui.langue,
    "idRv": idRv
  };
  // on fait la requête
  var sendMeBack = {
    "caller": evts.showResult
  };
  evts.execute([{
    "name": "supprimerRv",
    "post": post,
    "sendMeBack": sendMeBack
  }]);
};

// ------------------------ validation d'un RV
evts.validerRv = function () {
  // on cache la boîte de résa
  ui.resa.modal('hide');
  // on récupère les valeurs postées
  var idClient = $('#idClient option:selected').val();
  if (!idClient) {
    idClient = $('#idClient option').val();
  }
  // paramètres de la requête
  var sendMeBack = {
    "caller": evts.showResult
  };
  var post = {
    "user": ui.user,
    "lang": ui.langue,
    "jour": ui.jourAgenda,
    "idClient": idClient,
    "idCreneau": ui.idCreneau
  };
  // on fait la requête
  evts.execute([{
    "name": "validerRv",
    "post": post,
    "sendMeBack": sendMeBack
  }]);
};

// ------------------------ gestion de la réservation
evts.cancelDialogResa = function () {
  // on cache la boîte de dialogue
  ui.resa.modal('hide');
};

evts.reserverCreneau = function (id) {
  // on note le créneau
  ui.idCreneau = id;
  // on affiche la boîte de résa
  ui.resa.modal('show');
};


