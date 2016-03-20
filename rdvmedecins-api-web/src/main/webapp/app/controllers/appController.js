/**
 * Created by ST on 03/05/2014.
 */
angular.module("rdvmedecins")
  .controller("appCtrl", ['$scope', 'config', 'utils', '$location', '$locale',
    function ($scope, config, utils, $location, $locale) {

      // debug
      utils.debug("[app] init");

      // ----------------------------------------initialisation page
      // les modèles des # pages
      $scope.app = {waitingTimeBeforeTask: config.waitingTimeBeforeTask};
      $scope.login = {};
      $scope.home = {};
      $scope.agenda = {};
      $scope.resa = {};
      // modèle de la page courante
      var app = $scope.app;
      // [app.debug] et [utils.verbose] doivent toujours être synchronisés
      app.debug = utils.verbose;
      app.debug.on = config.debug;
      // pas de titre de page pour l'instant
      app.titre = {show: false};
      // pas de barres de navigation
      app.navbarrun = {show: false};
      app.navbarstart = {show: false};
      // pas d'erreurs
      app.errors = {show: false};
      // locale par défaut
      angular.copy(config.locales['fr'], $locale);
      // la vue courante
      app.view = {url: undefined, model: {}, done: false};
      // la tâche courante
      app.task = app.view.model.task = {action: utils.waitForSomeTime(app.waitingTimeBeforeTask), isFinished: false};

      // ---------------------------------- méthodes

      // annulation tâche courante
      app.cancel = function () {
        utils.debug("[app] cancel task");
        // on annule la tâche asynchrone de la vue courante
        var task = app.view.model.task;
        task.isFinished = true;
        task.action.reject();
        // message d'erreur
        app.errors = {title: {text: config.canceledOperation, values: {}}, messages: [], show: true};
        // fin attente
        app.waiting.show = false;
      };

      // déconnexion
      app.deconnecter = function () {
        // on revient à la page de login
        $location.path(config.urlLogin);
      };

      // ce code doit rester là car il référence la fonction [cancel] qui précède
      app.waiting = {title: {text: config.msgWaitingInit, values: {}}, cancel: app.cancel, show: true};
    }])
;

