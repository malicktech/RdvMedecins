/**
 * Created by ST on 14/05/2014.
 */

angular.module("rdvmedecins").directive('footable', ['$timeout', 'utils', function ($timeout, utils) {
  return {
    link: function (scope, element, attrs) {
      utils.debug("directive footable");
      $timeout(function () {
        $("#creneaux").footable();
      })
    }
  }
}]);