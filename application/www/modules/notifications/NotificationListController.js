angular.module('CRNSCtrl')
.controller('NotificationListCtrl', ['$scope', '$state', 'NotificationServices', function($scope, $state, NotificationServices) {
  $scope.listItems = NotificationServices.notificationList.data.data;

  $scope.openDetail = function(item) {
    $state.go('app.detail', {id: item.notification.id});
  };
}]);
