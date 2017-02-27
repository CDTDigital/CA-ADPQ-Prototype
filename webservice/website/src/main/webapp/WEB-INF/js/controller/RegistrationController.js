/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {

    function RegistrationController($scope, $timeout, HttpFactory, toaster, GooglePlacesFactory) {
        function onInit() {
            $scope.register = {};
            var addressObj = {};
            var input = document.getElementById('location');
            var autocomplete = GooglePlacesFactory.initAutoComplete(input);
            autocomplete.addListener('place_changed', function () {
                $scope.register.location = GooglePlacesFactory.getLocationDetails()
            });
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
        };

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
