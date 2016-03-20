/**
 * Created by ST on 03/05/2014.
 */
angular.module("rdvmedecins")
  .controller('homeCtrl', ['$scope', 'config', '$filter', '$translate', '$locale', '$location', 'utils', 'dao',
    function ($scope, config, $filter, $translate, $locale, $location, utils, dao) {

      // log
      utils.debug("[homeCtrl] init");

      // contrôle de navigation
      var lastView = $scope.app.view;
      utils.debug("[homeCtrl] lastView", lastView);
      var navigationAllowed = lastView.url == config.urlHome
        || lastView.url == config.urlAgenda
        || (lastView.url == config.urlLogin && lastView.done);
      if (!navigationAllowed) {
        // on ne bouge pas
        $location.path(lastView.url);
        return;
      }

      // -------------------------------- initialisation du modèle de la page
      // on récupère le modèle parent
      var home = $scope.home;
      var app = $scope.app;
      // vue courante
      app.view = {url: config.urlHome, model: home, done: false};
      // préparation du modèle
      app.navbarstart.show = false;
      app.navbarrun.show = true;
      app.errors.show = false;
      app.titre = {text: config.choixMedecinJourTitle, show: true, model: {}};
      // le modèle du calendrier
      var today = new Date();
      home.datepicker = { jour: today, minDate: today};
      // le menu
      app.menu = {afficherAgenda: true};
      // msg d'attente
      app.waiting.title = {text: config.msgWaiting, values: {}};
      app.waiting.show = false;
      // des infos dans la session ?
      if (home.jour) {
        home.datepicker.jour = home.jour;
      }


      // ------------------------------------- méthodes du modèle

      // changement de langue
      home.setLang = function (langKey) {
        // on change la langue courante
        $translate.use(langKey);
        // changement de locale
        angular.copy(config.locales[langKey], $locale);
        // modification jour pour appliquer la locale sur le calendrier
        home.datepicker.jour = new Date(home.datepicker.jour.getTime());
      };

      // affichage agenda
      home.afficherAgenda = function afficherAgenda() {
        // on récupère les informations
        var idMedecin = $('#' + app.medecins.id).selectpicker('val');
        var jour = home.datepicker.jour;
        utils.debug("[homeCtrl] idMedecin", idMedecin);
        utils.debug("[homeCtrl] jour", jour);

        // on met les infos dans la vue
        home.selectedValue = idMedecin;
        home.jour = jour;

        // on cherche le médecin sélectionné
        var selectedMedecin = undefined;
        if (idMedecin) {
          angular.forEach(app.medecins.data, function (medecin) {
            if (medecin.id == idMedecin) {
              selectedMedecin = medecin;
            }
          });
        }
        // erreur ?
        if (!selectedMedecin) {
          return;
        }
        // on met le jour au format yyyy-MM-dd
        app.formattedJour = $filter('date')(jour, 'yyyy-MM-dd');
        // préparation modèle pour la suite
        app.waiting.show = true;
        app.errors.show = false;
        // attente simulée
        var task = home.task = {action: utils.waitForSomeTime(app.waitingTimeBeforeTask), isFinished: false};
        // on demande l'agenda du médecin
        var promise = task.action.promise.then(function () {
          // tâche annulée ?
          if (task.isFinished) {
            return;
          }
          // le chemin de l'URL de service
          var path = config.urlSvrAgenda + "/" + idMedecin + "/" + app.formattedJour;
          // on demande l'agenda
          task = {action: dao.getData(app.serverUrl, app.username, app.password, path), isFinished: false};
          // on retourne la promesse d'achèvement de la tâche
          return task.action.promise;
        });
        // on analyse le résultat
        promise.then(function (result) {
          // fin d'attente
          app.waiting.show = false;
          // erreur ?
          if (result.err == 0) {
            // on met les données acquises dans la session
            app.agenda = result.data;
            // on passe à la vue suivante
            app.view.done = true;
            $location.path(config.urlAgenda);
          } else {
            // il y a eu des erreurs pour obtenir l'agenda
            app.errors = {
              title: {text: config.getAgendaErrors, values: {}},
              messages: utils.getErrors(result, $filter),
              show: true};
          }
        })
      };
    }
  ]);

