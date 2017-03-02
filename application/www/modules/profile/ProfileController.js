'use strict';
angular.module('CRNSCtrl')
.controller('ProfileCtrl', ['Notify', '$scope', 'GoogleMapService', 'AccountData', '$state', 'AccountServices', function(Notify, $scope, GoogleMapService, AccountData, $state, AccountServices) {
     $scope.profile = AccountData.getUserProfileData();
     $scope.fetchCurrentLocationData = function() {
        var tempData = GoogleMapService.getLocationAddress();
        $scope.profile.location.addressLine1 = tempData.address;
        $scope.profile.location.placeId = tempData.placeId;
        $scope.profile.location.latitude = tempData.latitude;
        $scope.profile.location.longitude = tempData.longitude;
        $scope.profile.location.zipCode = tempData.zipCode;

        console.log($scope.profile);
    };

    $scope.fetchCurrentLocationData();

    /* It will go on add location screen */
    $scope.onTapAddLocation = function() {
        $state.go('location');
    };

    $scope.onTapUpdateProfile = function() {
        if($scope.profile.firstName == '' || $scope.profile.lastName == '' || $scope.profile.mobileNumber == '') {
            Notify.errorToaster('You can not update profile with blank inputs!');
        } else if($scope.profile.location.addressLine1 == '') {
            Notify.errorToaster('Please add your location for getting the notifications!');
        } else {
            $scope.profile.mobileNo = String($scope.profile.mobileNumber);
            AccountServices.setUpAccount($scope.profile).then(function(resp) {
                Notify.successToaster('Profile Updated Successfully!');
            }, function(resp) {
                Notify.errorToaster(resp.data.message);
            });
        }
    };
}]);
