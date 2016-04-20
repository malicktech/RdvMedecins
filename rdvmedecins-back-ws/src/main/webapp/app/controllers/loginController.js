/**
 * Created by ST on 03/05/2014.
 */
angular.module("rdvmedecins")
  .controller('loginCtrl', ['$scope', 'config' , 'dao', '$location', '$filter', 'utils', '$translate', '$locale',
    function ($scope, config, dao, $location, $filter, utils, $translate, $locale) {

      // log
      utils.debug("[loginCtrl] init");

      // ---------------------------------------------------- initialisation
      // on récupère le modèle parent
      var login = $scope.login;
      var app = $scope.app;
      // vue courante
      app.view = {url: config.urlLogin, model: login, done: false};
      // on attend la fin du chargement de l'application
      app.task.action.promise.then(function () {
        // fin attente
        app.waiting.show = false;
        // navbar
        app.navbarstart.show = true;
        app.navbarrun.show = false;
        app.titre = {text: config.identification, show: true, model: {}};
        // credentials
        app.serverUrl = "http://localhost:8080/rdvmedecins-api-web";
        app.username = "admin";
        app.password = "admin";
      }, function () {
        // fin attente
        app.waiting.show = false;
        // l'attente a été annulée par l'utilisateur
        app.errors = { title: {text: config.loadingError, values: {}}, messages: [], show: true};
      });

      // --------------------------------------- méthodes

      // changement de langue
      login.setLang = function (langKey) {
        // on change la langue courante
        $translate.use(langKey);
        // changement de locale
        angular.copy(config.locales[langKey], $locale);
      };

      // authentification
      login.authenticate = function (serverUrl, username, password) {
        utils.debug("[loginCtrl] serverUrl=" + serverUrl + ", username=" + username + ", password=" + password);
        // on met en route l'attente
        app.waiting.show = true;
        app.waiting.title = {text: config.msgWaiting, values: {}};
        // au départ pas d'erreurs
        app.errors = {show: false};
        // attente simulée
        var task = login.task = {action: utils.waitForSomeTime(app.waitingTimeBeforeTask), isFinished: false};
        // on charge les médecins
        var promise = task.action.promise.then(function () {
          task.action = dao.getData(serverUrl, username, password, config.urlSvrMedecins);
          return task.action.promise;
        });
        // on analyse le résultat et on demande les clients
        promise = promise.then(function (result) {
          // tâche terminée ?
          if (task.isFinished) {
            return;
          }
          // on analyse le résultat de la tâche précédente
          if (result.err == 0) {
            // on met les données acquises dans le modèle
            app.medecins = {title: {text: config.listMedecinsTitle, values: {}}, data: result.data, show: true, id: 'medecins'};
            // on demande la liste des clients
            task.action = dao.getData(serverUrl, username, password, config.urlSvrClients);
            // on rend la promesse du résultat
            return task.action.promise;
          } else {
            // il y a eu des erreurs pour obtenir la liste des médecins
            app.errors = { title: {text: config.getMedecinsErrors, values: {}}, messages: utils.getErrors(result, $filter), show: true, model: {}};
            // tâche terminée
            task.isFinished = true;
          }
        });
        // on analyse le résultat de la demande des clients
        promise.then(function (result) {
          // fin d'attente
          app.waiting.show = false;
          // tâche terminée ?
          if (task.isFinished) {
            return;
          }
          // on analyse le résultat de la tâche précédente
          if (result.err == 0) {
            // on met les clients dans le modèle
            app.clients = {
              title: {text: config.listClientsTitle, values: {}},
              data: result.data, show: true, id: 'clients'};
            // on passe à la vue suivante
            app.view.done = true;
            $location.path(config.urlHome);
          } else {
            // il y a eu des erreurs pour obtenir la liste des clients
            app.errors = {
              title: {text: config.getClientsErrors, values: {}},
              messages: utils.getErrors(result, $filter), show: true, model: {}};
            // tâche terminée
            task.isFinished = true;
          }
        });
      };
    }
  ])
;
