'use strict';
angular.module('CRNSCtrl')
.controller('SetupCtrl', ['$scope', '$state', 'GoogleMapService', function($scope, $state, GoogleMapService) {
    $scope.currentAddress = GoogleMapService.getLocationAddress();

    /* It will go on add location screen */
    $scope.onTapAddLocation = function() {
        $state.go('location');
    };

    /* It will delete the added location and clear the view */
    $scope.onTapDeleteLocation = function() {
        GoogleMapService.setLocationAddress({address: '', placeId: ''});
        $scope.currentAddress = GoogleMapService.getLocationAddress();
    };

    /* For temp, It will directly jump on dashboard. */
    $scope.onTapProceedBtn = function() {
        $state.go('app.dash');
    };
}]);
