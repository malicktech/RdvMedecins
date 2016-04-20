/**
 * Created by ST on 03/05/2014.
 */
angular.module("rdvmedecins")
  .controller('resaCtrl', ['$scope', 'config', '$filter', '$translate', '$locale', '$location', 'utils', 'dao',
    function ($scope, config, $filter, $translate, $locale, $location, utils, dao) {

      // log
      utils.debug("[resaCtrl] init");

      // contrôle de navigation
      var lastView = $scope.app.view;
      utils.debug("[resaCtrl] lastView", lastView);
      var navigationAllowed = lastView.url == config.urlResa
        || (lastView.url == config.urlAgenda && lastView.done);
      if (!navigationAllowed) {
        // on ne bouge pas
        $location.path(lastView.url);
        return;
      }

      // ------------------------------- initialisation vue
      // on récupère le modèle parent
      var resa = $scope.resa;
      var app = $scope.app;
      // vue courante
      app.view = {url: config.urlResa, model: resa, done: false};
      // -------------------------------- modèle de la page
      // les barres de navigation
      app.navbarstart.show = false;
      app.navbarrun.show = true;
      // le message d'attente
      app.waiting.show = false;
      app.waiting.title = {text: config.msgWaiting, values: {}};
      // le message d'erreurs
      app.errors.show = false;
      // le titre de la page
      app.titre = {
        text: config.resaTitre, model: {titre: app.agenda.medecin.titre,
          prenom: app.agenda.medecin.prenom,
          nom: app.agenda.medecin.nom,
          jour: app.agenda.jour,
          creneau: app.selectedCreneau.creneau.text},
        show: true
      };
      // le menu de la page
      app.menu = {retourAgenda: true, validerRv: true};


      // --------------------------------------------------- méthodes de la page
      // changement de langue
      resa.setLang = function (langKey) {
        // on change la langue courante
        $translate.use(langKey);
        // changement de locale pour le calendrier
        angular.copy(config.locales[langKey], $locale);
      };

      // retour à l'agenda
      resa.retourAgenda = function () {
        // travail terminé
        app.view.done = true;
        // UI
        app.errors.show = false;
        app.waiting.show = false;
        // navigation
        $location.path(config.urlAgenda);
      };

      // validation d'un rendez-vous
      resa.validerRv = function () {
        // on récupère l'id du client
        var idClient = $('#' + app.clients.id).selectpicker('val');
        utils.debug("[resaCtrl] resa idClient", idClient);
        // initialisation modèle pendant l'attente
        app.waiting.show = true;
        app.errors.show = false;
        // attente simulée
        var task = resa.task = {action: utils.waitForSomeTime(app.waitingTimeBeforeTask), isFinished: false};
        // on ajoute le créneau
        var promise = task.action.promise.then(function () {
          // tâche terminée ?
          if (task.isFinished) {
            return;
          }
          // le chemin de l'URL de service
          var path = config.urlSvrResaAdd;
          // les données à transmettre au service
          var post = {jour: app.agenda.jour, idCreneau: app.selectedCreneau.creneau.id, idClient: idClient};
          // on lance la tâche asynchrone
          task.action = dao.getData(app.serverUrl, app.username, app.password, path, post);
          // on retourne la promesse d'achèvement de la tâche
          return task.action.promise;
        });

        // analyse du résultat de la tâche
        promise = promise.then(function (result) {
          utils.debug("[resaCtrl] resa success", result);
          // on analyse la réponse
          if (result.err != 0) {
            // il y a eu des erreurs pour valider le rv
            app.errors.title = {text: config.postResaErrors, values: {}};
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
          utils.debug("[resaCtrl] agenda success", result);
          // fin d'attente
          app.waiting.show = false;
          // tâche terminée ?
          if (task.isFinished) {
            return;
          }
          // on analyse la réponse
          if (result.err != 0) {
            // il y a eu des erreurs pour obtenir l'agenda
            app.errors = {title: {text: config.getAgendaErrors, values: {}}, messages: utils.getErrors(result, $filter), show: true};
          } else {
            // on met les résultats dans le modèle
            app.agenda = result.data;
            // on passe à la vue suivante
            app.view.done = true;
            $location.path(config.urlAgenda);
          }
        });
      }
    }]);

