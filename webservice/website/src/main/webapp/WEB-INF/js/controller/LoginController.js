/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {
    'use strict'

    /**
     * LoginController responsible for view login fo login.html
     * @param $scope
     * @param $rootScope
     * @param HttpFactory
     * @param toaster
     * @constructor
     */
    function LoginController($scope, $rootScope, HttpFactory, toaster) {

        /**
         * LoginController on load function. Redirects to account page if already logged in
         */
        function onLoad() {
            if (HttpFactory.isLoggedIn()) {
                $scope.go('/account')
            }
        }

        /**
         * Submit a post request to login with credentials
         */
        $scope.submitLogin = function () {
            if ($scope.signin.$valid) {
                $scope.isLoading = true;
                HttpFactory.login($scope.user.email, $scope.user.password).then(function (loginResponse) {
                    //$rootScope.isLoggedIn = true;
                    //$rootScope.user_role = "USER";
                    $scope.go('/history')
                }, function (error) {
                    $scope.isLoading = false;
                    toaster.pop('error', error.message);
                })
            }
            else {
                toaster.pop('error', "Please fill all the required fields");
            }
        };

        onLoad();
    }

    var app = angular.module('CRNS'),
        requires = [
            '$scope',
            '$rootScope',
            'modules.core.HttpFactory',
            'toaster',
            LoginController
        ];
    app.controller('LoginController', requires)
}());
