/**
 * Created by sunil.jhamnani on 3/1/17.
 */

(function () {
    'use strict';
    /**
     * MessageController responsible for view logic of message.html to publish notifications
     * @param $scope
     * @param $timeout
     * @param $filter
     * @param HttpFactory
     * @constructor
     */
    function MessageController($scope, $timeout, $filter, toaster, HttpFactory) {

        /**
         * MessageController initialization function
         */
        function onInit() {
            $scope.locationDetails = {};
            $scope.notification = {};
        }

        /**
         * Public notication with all the required details
         */
        $scope.publicNotification = function () {
            if ($scope.notificationForm.$valid) {
                $scope.isLoading = true;
                $timeout(function () {
                    var location = getLocationObject($scope.locationDetails.location);
                    angular.extend($scope.notification, {
                        address: location.formatted_address,
                        city: location.city,
                        message: $scope.message,
                        subject: $scope.subject,
                        validThrough: $filter('date')(Date.now() + ($scope.validThrough * 60000), 'yyyy-MM-ddThh:mm:ss', '+0000'),
                        latitude: location.latitude,
                        longitude: location.longitude,
                        zipCode: location.zipCode,
                        sentBy: {
                            id: HttpFactory.getUserId()
                        }
                    });
                    HttpFactory.pushNotification($scope.notification).then(function (response) {
                        toaster.pop('info', "Notification Pushed");
                        $scope.go('/history');
                    }, function () {
                        toaster.pop('error', "Error! Please try again");
                        $scope.isLoading = false;
                    })
                },1000);
            }
            else {
                toaster.pop('error', "Please fill all the required fields");
            }
        };

        /**
         * Create location object using details from $scope.locationDetails
         * @param loc
         * @returns {{formatted_address: *, addressLine1: *, addressLine2: *, city: *, zipCode: *, latitude: *, longitude: *}}
         */
        function getLocationObject(loc) {
            var addressObj = {};
            if (loc.address_components) {
                loc.address_components.forEach(function (elem) {
                    addressObj[elem.types[0]] = elem.long_name
                });
                return {
                    formatted_address: loc.formatted_address,
                    addressLine1: addressObj.street_number,
                    addressLine2: addressObj.route,
                    city: addressObj.locality,
                    zipCode: addressObj.postal_code,
                    latitude: loc.geometry.location.lat(),
                    longitude: loc.geometry.location.lng()
                };
            }
        }

        onInit();
    }

    var app = angular.module("CRNS"),
        requires = [
            '$scope',
            '$timeout',
            '$filter',
            'toaster',
            'modules.core.HttpFactory',
            MessageController
        ];
    app.controller('MessageController', requires);
}());
