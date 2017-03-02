/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {
    'use strict';

    /**
     * RegistrationController responsible for view logic of registration.html
     * @param $scope
     * @param $timeout
     * @param HttpFactory
     * @param toaster
     * @param GooglePlacesFactory
     * @constructor
     */
    function RegistrationController($scope, $timeout, HttpFactory, toaster, GooglePlacesFactory) {

        /**
         * RegistrationController initialization function
         */
        function onInit() {
            $scope.register = {};
        }

        /**
         * Submit the registeration details to API
         */
        $scope.submit = function () {
            if($scope.registerForm.$valid) {
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
                    $scope.isLoading = false;
                    toaster.pop('error', "Something went wrong while registering the user");
                })
            }
            else {
                toaster.pop('error', "Please fill all the required fields");
            }
        };

        /**
         * Create location object using details from $scope.locationDetails
         * @param loc
         * @returns {{addressLine1: *, addressLine2: *, city: *, zipCode: *, placeId: *, latitude: *, longitude: *, currentLocation: boolean}}
         */
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
                    currentLocation: false,
                    accountSetupDone: true
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
