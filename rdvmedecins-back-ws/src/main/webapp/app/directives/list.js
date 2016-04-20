/**
 * Created by ST on 06/05/2014.
 */
angular.module("rdvmedecins")
  .directive("list", ['utils', '$timeout', function (utils, $timeout) {
    // instance de la directive retournée
    return {
      // élément HTML
      restrict: "E",
      // url du fragment
      templateUrl: "views/list.html",
      // scope unique à chaque instance de la directive
      scope: true,
      // fonction lien avec le document
      link: function (scope, element, attrs) {
        // à chaque fois que attrs["model"] change, le modèle de la directive doit changer également
        scope.$watch(attrs["model"], function (newValue) {
          utils.debug("directive list newValue", newValue);
          // on met à jour le modèle de la directive
          scope.model = newValue;
          $timeout(function () {
            $('#' + scope.model.id).selectpicker('refresh');
          })
        });
      }
    }
  }]);