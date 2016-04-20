/**
 * Created by ST on 05/05/2014.
 */
angular.module("rdvmedecins")
  .factory('utils', ['config', '$timeout', '$q', function (config, $timeout, $q) {

    // mode debug
    var verbose = {};
    // attente
    var waitForSomeTime = function (milliseconds) {
      // attente asynchrone de milliseconds milli-secondes
      var task = $q.defer();
      $timeout(function () {
        task.resolve(true);
      }, milliseconds);
      // on retourne la tâche
      return task;
    };

    // affichage de la représentation Json d'un objet
    function debug(message, data) {
      if (verbose.on) {
        var text = data ? message + " : " + angular.toJson(data) : message;
        console.log(text);
      }
    }

    // analyse des erreurs dans la réponse du serveur JSON
    function getErrors(data) {
      // data {err:n, messages:[]}, err!=0
      // erreurs
      var errors = [];
      // code d'erreur
      var err = data.err;
      switch (err) {
        case 2 :
          // not authorized
          errors.push('not_authorized');
          break;
        case 3 :
          // forbidden
          errors.push('forbidden');
          break;
        case 4 :
          // erreur locale
          errors.push('not_http_error');
          break;
        case 6 :
          // document non trouvé
          errors.push('not_found');
          break;
        default :
          // autres cas
          errors = data.messages;
          break;
      }
      // si pas de msg, on en met un
      if (!errors || errors.length == 0) {
        errors = ['error_unknown'];
      }
      // on rend la liste des erreurs
      return errors;
    }

    // mise en forme du libellé d'un créneau horaire
    function getTextForCreneau(creneau) {
      return getTextFor(creneau.hDebut) + "h" + getTextFor(creneau.mDebut) + ":" + getTextFor(creneau.hFin) + "h" + getTextFor(creneau.mFin);
    }

    // mise en forme du libellé d'un créneau horaire - 2
    function getTextFor(number) {
      // met un 0 devant le chiffre si moins de deux chiffres
      var text = "" + number;
      if (text.length < 2) {
        text = "0" + text;
      }
      return text;
    }

    // instance du service
    return {
      waitForSomeTime: waitForSomeTime,
      debug: debug,
      getErrors: getErrors,
      getTextForCreneau: getTextForCreneau,
      verbose: verbose
    };
  }]);
