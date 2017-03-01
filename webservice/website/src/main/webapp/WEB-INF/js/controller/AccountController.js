/**
 * Created by sunil.jhamnani on 2/26/17.
 */
(function () {
    function AccountController($scope, $sessionStorage, HttpFactory, GooglePlacesFactory, toaster) {
        function onInit() {
            HttpFactory.getUserProfile().then(function (userData) {
                $scope.account = userData;
                if ($scope.account.location.placeId) {
                    var place = GooglePlacesFactory.getPlaceInformation($scope.account.location.placeId, function (result,status) {
                        if (status === google.maps.GeocoderStatus.OK) {
                            $scope.name = result[0].formatted_address;
                            $scope.$apply();
                        }
                    });
                    //$scope.selectedLocation = $scope.account.location.addressLine1 + ', ' +
                    //    $scope.account.location.addressLine2 + ', ' +
                    //    $scope.account.location.city + ', ' +
                    //    $scope.account.location.zipCode;
                }
            }, function (error) {
                toaster.pop('error', error.message);
            });
        }

        $scope.editDetails = function () {
            $scope.account.location = getLocationObject($scope.locationDetails);
            HttpFactory.setUserProfile($scope.account).then(function (res) {
                $scope.account = res;
                toaster.pop('success', "Profile Updated successfully");
            })
        };

        $scope.logout = function () {
            Object.keys($sessionStorage.loginResponse).forEach(function (key) {
                delete $sessionStorage.loginResponse[key];
            });
            //$scope.isLoggedIn = false;
            $scope.go('/login');
        }

        function getLocationObject(loc) {
            var addressObj={};
            if(loc.address_components) {
                loc.address_components.forEach(function(elem){
                    addressObj[elem.types[0]] = elem.long_name
                });
                return  {
                    addressLine1: addressObj.street_number,
                    addressLine2: addressObj.route,
                    city: addressObj.locality,
                    zipCode: addressObj.postal_code,
                    placeId: loc.place_id,
                    latitude: loc.geometry.location.lat(),
                    longitude: loc.geometry.location.lng(),
                    currentLocation: false
                };
            }
        }

        onInit();
    }
    var app = angular.module('CRNS'),
        requires = [
            '$scope',
            '$sessionStorage',
            'modules.core.HttpFactory',
            'modules.google.GooglePlacesFactory',
            'toaster',
            AccountController
        ];
    app.controller('AccountController', requires)
}());
