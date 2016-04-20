// ----------------------- interface [DAO]

// URL exposées par le serveur
dao.urls = {
  "login": "/getLogin",
  "accueil": "/getAccueil",
  "jumbotron": "/getJumbotron",
  "agenda": "/getAgenda",
  "supprimerRv": "/supprimerRv",
  "validerRv": "/validerRv",
  "navbar-start": "/getNavbarStart",
  "navbar-run": "/getNavbarRun",
  "accueil-sans-agenda": "/getNavbarRunJumbotronAccueil",
  "accueil-avec-agenda": "/getNavbarRunJumbotronAccueilAgenda"
};

// --------------- interface

// url serveur
dao.setUrlService = function (urlService) {
  dao.urlService = urlService;
};

// ------------------ gestion générique des actions

// exécution d'une suite d'actions
dao.doActions = function (actions, done) {
  // traitement des actions
  dao.actionsCount = actions.length;
  dao.actionIndex = 0;
  for (var i = 0; i < dao.actionsCount; i++) {
    // requête DAO asynchrone
    var deferred = $.Deferred();
    deferred.done(dao.actionDone);
    dao.doAction(deferred, actions[i], done);
  }
};

// -------------------- fonctions privées

// exécution d'une action
dao.doAction = function (deferred, action, done) {
  // fonction done à embarquer dans l'action
  if (action.sendMeBack) {
    action.sendMeBack.done = done;
  } else {
    action.sendMeBack = {
      "done": done
    };
  }
  // exécution action
  dao.executePost(deferred, action.sendMeBack, dao.urls[action.name], action.post)
};

// on a reçu un résultat
dao.actionDone = function (result) {
  // caller ?
  var sendMeBack = result.sendMeBack;
  if (sendMeBack && sendMeBack.caller) {
    sendMeBack.caller(result);
  }
  // next ?
  if (sendMeBack && sendMeBack.next) {
    // requête DAO asynchrone
    var deferred = $.Deferred();
    deferred.done(dao.actionDone);
    dao.doAction(deferred, sendMeBack.next, sendMeBack.done);
  }
  // fini ?
  dao.actionIndex++;
  if (dao.actionIndex == dao.actionsCount) {
    // done ?
    if (sendMeBack && sendMeBack.done) {
      sendMeBack.done(result);
    }
  }
};

// requête HTTP
dao.executePost = function (deferred, sendMeBack, url, post) {
  // on fait un appel Ajax à la main
  $.ajax({
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    url: dao.urlService + url,
    type: 'POST',
    data: JSON3.stringify(post),
    dataType: 'json',
    success: function (data) {
      // on rend le résultat
      deferred.resolve({
        "status": 1,
        "data": data,
        "sendMeBack": sendMeBack
      });
    },
    error: function (jqXHR, textStatus, errorThrown) {
      var data;
      if (jqXHR.responseText) {
        data = jqXHR.responseText;
      } else {
        data = textStatus;
      }
      // on rend l'erreur
      deferred.resolve({
        "status": 2,
        "data": data,
        "sendMeBack": sendMeBack
      });
    }
  });
};
