'use strict';
angular.module('CRNSCtrl')
.controller('SetupCtrl', ['$scope', '$state', 'GoogleMapService', 'AccountData', 'Notify', 'AccountServices', function($scope, $state, GoogleMapService, AccountData, Notify, AccountServices) {
    $scope.account = AccountData.getCurrentData();
    $scope.fetchCurrentLocationData = function() {
        var tempData = GoogleMapService.getLocationAddress();
        $scope.account.location.addressLine1 = tempData.address;
        $scope.account.location.placeId = tempData.placeId;
        $scope.account.location.latitude = tempData.latitude;
        $scope.account.location.longitude = tempData.longitude;
    };

    $scope.fetchCurrentLocationData();

    /* It will go on add location screen */
    $scope.onTapAddLocation = function() {
        AccountData.setCurrentData($scope.account);
        $state.go('location');
    };

    /* It will delete the added location and clear the view */
    $scope.onTapDeleteLocation = function() {
        GoogleMapService.setLocationAddress({address: '', placeId: '', latitude: '', longitude: ''});
        $scope.fetchCurrentLocationData();
    };

    /* For temp, It will directly jump on dashboard. */
    $scope.onTapContinueBtn = function() {
        if($scope.account.firstName == '' || $scope.account.lastName == '' || $scope.account.mobileNumber == '') {
            Notify.errorToaster('Please fill all the input fields!');
        } else if($scope.account.address == '') {
            Notify.errorToaster('Please add your location for getting the notifications!');
        } else {
            $scope.account.mobileNo = String($scope.account.mobileNo);
            AccountServices.setUpAccount($scope.account).then(function(resp) {
                console.log(resp);
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
