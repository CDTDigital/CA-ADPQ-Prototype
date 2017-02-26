/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {

    function RegistrationController($scope, $timeout, HttpFactory, toaster) {
        function onInit() {
            var input = document.getElementById('location');
            var autocomplete = new google.maps.places.Autocomplete(input);
            autocomplete.addListener('place_changed', function (resp) {
                var placeObject = autocomplete.getPlace();
                var addressfields = placeObject.formatted_address.split(',');
                var addressLine1 = addressfields[0];
                var addressLine2 = "";
                var city = addressfields[1];
                var state = addressfields[2].split(" ")[1];
                var zipCode = addressfields[2].split(" ")[2];
                var lat = placeObject.geometry.location.lat();
                var lon = placeObject.geometry.location.lng();
                var placeId = placeObject.place_id;
                $scope.location = {
                    addressLine1: addressLine1,
                    addressLine2: addressLine2,
                    city: city,
                    zipCode: zipCode,
                    placeId: placeId,
                    lattitude: lat,
                    longitude: lon,
                    currentLocation: false
                }
            })
        }
        $scope.submit = function () {
            if ($scope.register.password != $scope.confirmPassword) {
                toaster.pop('error', "Password and Confirm password are not matching");
                return
            }

            HttpFactory.signup($scope.register).then(function (response) {
                toaster.pop('info', "User successfully registered");
                $timeout(function () {
                    $scope.go('/login');
                }, 2000);
            }, function (error) {
                toaster.pop('error', error.message);
            })
        }

        onInit();
    }

    var app = angular.module('CRNS'),
        requires = [
            '$scope',
            '$timeout',
            'modules.core.HttpFactory',
            'toaster',
            RegistrationController
        ];
    app.controller('RegistrationController', requires)
}());
