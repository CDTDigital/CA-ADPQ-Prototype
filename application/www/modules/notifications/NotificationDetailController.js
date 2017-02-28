'use strict';
angular.module('CRNSCtrl')
.controller('NotificationDetailCtrl', ['$scope', 'NotificationServices',
  function($scope, NotificationServices) {
    $scope.detailObj = NotificationServices.getNotificationDetail();
    console.log($scope.detailObj);

    $scope.mapObjects = [{
      'Latitude': $scope.detailObj.notification.latitude,
      'Longitude': $scope.detailObj.notification.longitude
    }];
}]);
