/**
 * Created by ST on 03/05/2014.
 */
angular.module("rdvmedecins")
  .controller('agendaCtrl', ['$scope', 'config', '$filter', '$translate', '$locale', '$location', 'utils', 'dao',
    function ($scope, config, $filter, $translate, $locale, $location, utils, dao) {

      // log
      utils.debug("[agendaCtrl] init");
      // contrôle de navigation
      var lastView = $scope.app.view;
      utils.debug("[agendaCtrl] lastView", lastView);
      var navigationAllowed = lastView.url == config.urlAgenda
        || lastView.url == config.urlResa
        || (lastView.url == config.urlHome && lastView.done);
      if (!navigationAllowed) {
        // on ne bouge pas
        $location.path(lastView.url);
        return;
      }
      // -------------------------------- modèle de la page
      // on récupère le modèle parent
      var agenda = $scope.agenda;
      var app = $scope.app;
      // vue courante
      app.view = {url: config.urlAgenda, model: agenda, done: false};
      // les barres de navigation
      app.navbarstart.show = false;
      app.navbarrun.show = true;
      // le message d'attente
      app.waiting.show = false;
      app.waiting.title = {text: config.msgWaiting, values: {}};
      // le message d'erreurs
      app.errors.show = false;
      // le titre de la vue
      app.titre = {
        text: config.agendaTitre,
        model: {
          titre: app.agenda.medecin.titre,
          prenom: app.agenda.medecin.prenom,
          nom: app.agenda.medecin.nom,
          jour: app.agenda.jour},
        show: true
      };
      // le menu de la vue
      app.menu = {home: true};
      // mise en forme des créneaux
      var creneauxMedecin = app.agenda.creneauxMedecin;
      angular.forEach(creneauxMedecin, function (creneauMedecin) {
        var creneau = creneauMedecin.creneau;
        if (!creneau.text) {
          creneau.text = utils.getTextForCreneau(creneau);
        }
      });

      // -------------------------------- méthodes de la page

      // changement de langue
      agenda.setLang = function (langKey) {
        // on change la langue courante
        $translate.use(langKey);
        // changement de locale
        angular.copy(config.locales[langKey], $locale);
      };

      // retour à la vue [home]
      agenda.home = function () {
        // travail terminé
        app.view.done = true;
        // UI
        app.errors.show = false;
        app.waiting.show = false;
        // navigation
        $location.path(config.urlHome)
      };

      // réservation d'un créneau
      agenda.reserver = function (creneauId) {
        // réservation d'un nouveau créneau
        utils.debug("[agendaCtrl] réserver crenauId", creneauId);
        // on recherche dans le modèle le créneau concerné
        var done = false;
        for (var i = 0; i < creneauxMedecin.length && !done; i++) {
          var creneauMedecin = creneauxMedecin[i];
          if (creneauMedecin.creneau.id == creneauId) {
            // on met le créneau dans le modèle
            app.selectedCreneau = creneauMedecin;
            // trouvé
            done = true;
          }
        }
        utils.debug("[agendaCtrl] selectedCreneau", app.selectedCreneau);
        // on passe à la page de réservation
        app.view.done = true;
        $location.path(config.urlResa);
      };


      // suppression d'un rendez-vous
      agenda.supprimer = function (idRv) {
        utils.debug("[agendaCtrl] supprimer idRv", idRv);
        // initialisation modèle pendant l'attente
        app.waiting.show = true;
        app.errors.show = false;
        // attente simulée
        var task = agenda.task = {action: utils.waitForSomeTime(app.waitingTimeBeforeTask), isFinished: false};
        // on supprime le Rv
        var promise = task.action.promise.then(function () {
          // tâche terminée ?
          if (task.isFinished) {
            return;
          }
          // le chemin de l'URL de service
          var path = config.urlSvrResaRemove;
          // les données à transmettre au service
          var post = {idRv: idRv};
          // on supprime le Rv
          task.action = dao.getData(app.serverUrl, app.username, app.password, path, post);
          // on retourne la promesse d'achèvement de la tâche
          return task.action.promise;
        });

        // analyse du résultat de la tâche
        promise = promise.then(function (result) {
          utils.debug("[agendaCtrl] suppression success", result);
          // tâche terminée ?
          if (task.isFinished) {
            return;
          }
          // on analyse la réponse
          if (result.err != 0) {
            // il y a eu des erreurs pour supprimer le rv
            app.errors.title = {text: config.postRemoveErrors, model: {}};
            app.errors.messages = utils.getErrors(result, $filter);
            app.errors.show = true;
            // fin de tâche
            task.isFinished = true;
          }
        });
        // on redemande l'agenda du médecin
        promise = promise.then(function () {
          // tâche finie ?
          if (task.isFinished) {
            return;
          }
          // le chemin de l'URL de service
          var path = config.urlSvrAgenda + "/" + app.agenda.medecin.id + "/" + app.agenda.jour;
          // on lance la tâche asynchrone
          task.action = dao.getData(app.serverUrl, app.username, app.password, path);
          // on retourne la promesse d'achèvement de la tâche
          return task.action.promise;
        });

        // analyse du résultat de la tâche
        promise.then(function (result) {
          utils.debug("[agendaCtrl] agenda success", result);
          // fin d'attente
          app.waiting.show = false;
          // tâche terminée ?
          if (task.isFinished) {
            return;
          }
          // on analyse la réponse
          if (result.err != 0) {
            // il y a eu des erreurs pour obtenir l'agenda
            app.errors.title = {text: config.getAgendaErrors, values: {}};
            app.errors.messages = utils.getErrors(result, $filter);
            app.errors.show = true;
            // on ne continue pas
            return;
          }
          // on met le nouvel agenda dans le modèle
          app.agenda = result.data;
          creneauxMedecin = app.agenda.creneauxMedecin;
          // mise en forme des créneaux
          angular.forEach(creneauxMedecin, function (creneauMedecin) {
            var creneau = creneauMedecin.creneau;
            if (!creneau.text) {
              creneau.text = utils.getTextForCreneau(creneau);
            }
          });
        });
      }
    }
  ])
;

