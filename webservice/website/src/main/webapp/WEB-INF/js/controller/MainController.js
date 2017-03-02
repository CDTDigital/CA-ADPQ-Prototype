/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {
    'use strict'

    /**
     * MainController responsible for view logic of index.html
     * @param $scope
     * @param $rootScope
     * @param $window
     * @param $location
     * @param $sessionStorage
     * @param HttpFactory
     * @constructor
     */
    function MainController($scope, $rootScope, $window, $location, $sessionStorage, HttpFactory) {

        /**
         * Redirect page to specified route
         * @param path
         */
        $rootScope.go = function (path) {
            $location.url(path);
        };

        /**
         * Reload the page
         */
        $rootScope.reload = function () {
            $window.location.reload();
        };

        /**
         * Clear the session varible and redirect to login screen
         */
        $scope.logout = function () {
            HttpFactory.logoutUser().then(function (response) {
                Object.keys($sessionStorage.loginResponse).forEach(function (key) {
                    delete $sessionStorage.loginResponse[key];
                });
                $rootScope.isLoggedIn = false;

                $rootScope.go('/login');
            })

        };

        /**
         * Enable click on logo icon if user is logged in
         */
        $scope.toNotfiPage = function () {
            if ($rootScope.isLoggedIn) {
                $rootScope.go('/history');
            }
        }
    }

    var app = angular.module("CRNS"),
        requires = [
            '$scope',
            '$rootScope',
            '$window',
            '$location',
            '$sessionStorage',
            'modules.core.HttpFactory',
            MainController
        ];
    app.controller("MainController", requires)
}());
