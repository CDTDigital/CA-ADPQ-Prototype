/**
 * Created by sunil.jhamnani on 2/26/17.
 */
(function () {

    'use strict'

    /**
     * AccountController responsible for view logic of account.html to manage account information
     * @param $scope
     * @param HttpFactory
     * @param GooglePlacesFactory
     * @param toaster
     * @constructor
     */
    function AccountController($scope, $timeout, HttpFactory, GooglePlacesFactory, toaster) {

        /**
         * AccountController initialization function
         */
        function onInit() {
            $scope.isLoading = true;
            $scope.locationDetails = {};
            $scope.confirmPassword = null;
            HttpFactory.getUserProfile().then(function (userData) {
                $scope.account = userData;
                if ($scope.account.location) {
                    var place = GooglePlacesFactory.getPlaceInformation($scope.account.location.placeId, function (result, status) {
                        if (status === google.maps.GeocoderStatus.OK) {
                            $scope.name = result[0].formatted_address;
                            $scope.$apply();
                        }
                    });
                }
                $scope.isLoading = false;
            }, function (error) {
                toaster.pop('error', error.message);
                $scope.isLoading = false
            });
        }

        /**
         * Function to submit request for editing user details
         */
        $scope.editDetails = function () {
            if($scope.userDetails.$valid) {
                if (!angular.equals($scope.account.password, $scope.confirmPassword)) {
                    toaster.pop('error', "Password and Confirm password are not matching");
                    return
                }

                $scope.account.location = getLocationObject($scope.locationDetails.location);
                if (!$scope.account.location.zipCode) {
                    toaster.pop('error', "Zip code is not available. Please enter valid street address!");
                    return;
                }
                $scope.isLoading = true;
                HttpFactory.setUserProfile($scope.account).then(function (res) {
                    $scope.account = res;
                    toaster.pop('success', "Profile Updated successfully");
                    $timeout(function () {
                        $scope.go('/history')
                    },2000);
                    $scope.isLoading = false
                })
            }
        };

        /**
         * Function to create location object using details from $scope.locationDetails
         * @param loc
         * @returns {{addressLine1: *, addressLine2: *, city: *, zipCode: *, placeId: *, latitude: *, longitude: *, currentLocation: boolean}}
         */
        function getLocationObject(loc) {
            var addressObj = {};
            if (loc) {
                loc.address_components.forEach(function (elem) {
                    addressObj[elem.types[0]] = elem.long_name
                });
                return {
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
            return $scope.account.location;
        }

        onInit();
    }

    var app = angular.module('CRNS'),
        requires = [
            '$scope',
            '$timeout',
            'modules.core.HttpFactory',
            'modules.google.GooglePlacesFactory',
            'toaster',
            AccountController
        ];
    app.controller('AccountController', requires)
}());
