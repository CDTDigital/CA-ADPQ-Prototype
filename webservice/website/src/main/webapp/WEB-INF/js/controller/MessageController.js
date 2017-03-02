/**
 * Created by sunil.jhamnani on 3/1/17.
 */

(function () {
    'use strict';

    function MessageController($scope, $timeout, $filter, HttpFactory) {
        function onInit() {
            $scope.notification = {};
        }
        $scope.publicNotification = function () {
            $timeout(function () {
                var startDate = $scope.startDate.getFullYear() + '-' + $scope.startDate.getMonth() + '-' + $scope.startDate.getDate();
                var validThrough = $scope.validThrough.getFullYear() + '-' + $scope.validThrough.getMonth() + '-' + $scope.validThrough.getDate();
                var location = getLocationObject($scope.locationDetails);
                angular.extend($scope.notification, {
                    address: location.formatted_address,
                    city: location.city,
                    message: $scope.message,
                    sentTime: startDate + $filter('date')(Date.now(), 'Thh:mm:ss'),
                    subject: $scope.subject,
                    validThrough: validThrough + $filter('date')(Date.now(), 'Thh:mm:ss'),
                    latitude: location.latitude,
                    longitude: location.longitude,
                    zipCode: location.zipCode,
                    sentBy: {
                        id: HttpFactory.getUserId()
                    }
                });
                HttpFactory.pushNotification($scope.notification).then(function (response) {
                    toaster.pop('info', "Notification Pushed");
                    $scope.notification = {};
                }, function () {
                    toaster.pop('error', "Error! Please try again");
                })
            },1000);
        }

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
            'modules.core.HttpFactory',
            MessageController
        ];
    app.controller('MessageController', requires);
}());