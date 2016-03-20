/**
 * Created by ST on 05/05/2014.
 */
angular.module("rdvmedecins")
  .factory('dao', ['$http', '$q', 'config', '$base64', 'utils',
    function ($http, $q, config, $base64, utils) {

      // log
      utils.debug("[dao] init");

      // liste de données
      function getData(serverUrl, username, password, urlAction, info) {
        // opération asynchrone
        var task = $q.defer();
        // url requête HTTP
        var url = serverUrl + urlAction;
        // authentification basique
        var basic = "Basic " + $base64.encode(username + ":" + password);
        // la réponse
        var réponse;
        // les requêtes http doivent être toutes authentifiées
        var headers = $http.defaults.headers.common;
        headers.Authorization = basic;
        // on fait la requête HTTP
        var promise;
        if (info) {
          promise = $http.post(url, info, {timeout: config.timeout});
        } else {
          promise = $http.get(url, {timeout: config.timeout});
        }
        promise.then(success, failure);
        // on retourne la tâche elle-même afin qu'elle puisse être annulée
        return task;

        // success
        function success(response) {
          // response.data={status:0, data:[med1, med2, ...]} ou {status:x, data=[msg1, msg2, ...]
          utils.debug("[dao] getData[" + urlAction + "] success réponse", response);
          // réponse
          var payLoad = response.data;
          réponse = payLoad.status == 0 ? {err: 0, data: payLoad.data} : {err: 1, messages: payLoad.data};
          // on rend la réponse
          task.resolve(réponse);
        }

        // failure
        function failure(response) {
          utils.debug("[dao] getData[" + urlAction + "] error réponse", response);
          // on analyse le status
          var status = response.status;
          var error;
          switch (status) {
            case 401 :
              // unauthorized
              error = 2;
              break;
            case 403:
              // forbidden
              error = 3;
              break;
            case 404:
              // not found
              error = 6;
              break;
            case 0:
              // erreur locale
              error = 4;
              break;
            default:
              // autre chose
              error = 5;
          }
          // on rend la réponse
          task.resolve({err: error, messages: [response.statusText]});
        }
      }

      // --------------------- instance du service [dao]
      return {
        getData: getData
      }
    }]);
