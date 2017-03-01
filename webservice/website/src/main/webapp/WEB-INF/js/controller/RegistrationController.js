/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {

    function RegistrationController($scope, $timeout, HttpFactory, toaster, GooglePlacesFactory) {
        function onInit() {
            $scope.register = {};
        }

        $scope.submit = function () {
            $scope.register.location = getLocationObject($scope.locationDetails);
            if ($scope.register.password != $scope.confirmPassword) {
                toaster.pop('error', "Password and Confirm password are not matching");
                return
            }
            $scope.isLoading = true;
            HttpFactory.signup($scope.register).then(function (response) {
                toaster.pop('info', "User successfully registered");
                $timeout(function () {
                    $scope.go('/login');
                }, 2000);
            }, function (error) {
                toaster.pop('error', error.message);
            })
        };

        function getLocationObject(loc) {
            var addressObj={};
            if(loc.address_components) {
                loc.address_components.forEach(function(elem){
                    addressObj[elem.types[0]] = elem.long_name
                });
                return  {
                    addressLine1: loc.formatted_address,
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
            '$timeout',
            'modules.core.HttpFactory',
            'toaster',
            'modules.google.GooglePlacesFactory',
            RegistrationController
        ];
    app.controller('RegistrationController', requires)
}());
