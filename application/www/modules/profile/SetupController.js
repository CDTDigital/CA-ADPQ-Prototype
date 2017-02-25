'use strict';
angular.module('CRNSCtrl')
.controller('SetupCtrl', ['$scope', '$state', 'GoogleMapService', 'AccountData', 'Notify', 'AuthServices', function($scope, $state, GoogleMapService, AccountData, Notify, AuthServices) {
    $scope.account = AccountData.getCurrentData();
    $scope.currentAddress = GoogleMapService.getLocationAddress();
    $scope.account.address = $scope.currentAddress.address;
    $scope.account.placeId = $scope.currentAddress.placeId;

    /* It will go on add location screen */
    $scope.onTapAddLocation = function() {
        AccountData.setCurrentData($scope.account);
        $state.go('location');
    };

    /* It will delete the added location and clear the view */
    $scope.onTapDeleteLocation = function() {
        GoogleMapService.setLocationAddress({address: '', placeId: ''});
        $scope.currentAddress = GoogleMapService.getLocationAddress();
        $scope.account.address = $scope.currentAddress.address;
        $scope.account.placeId = $scope.currentAddress.placeId;
    };

    /* For temp, It will directly jump on dashboard. */
    $scope.onTapContinueBtn = function() {
        console.log($scope.account);
        if($scope.account.firstName == '' || $scope.account.lastName == '' || $scope.account.mobileNumber == '') {
            Notify.errorToaster('Please fill all the input fields!');
        } else if($scope.account.address == '') {
            Notify.errorToaster('Please add your location for getting the notifications!');
        } else {
            AuthServices.register($scope.account).then(function(resp) {
                if(resp.data && resp.data.responseStatus == 'SUCCESS') {
                    localStorage.setItem('accountSetup', true);
                    $state.go('app.dash');
                } else {
                    Notify.errorToaster(resp.data.message);
                };
            });
        }
    };
}]);
