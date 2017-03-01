/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {

    function MainController($scope, $rootScope, $window, $location, $sessionStorage, HttpFactory) {
        $rootScope.go = function (path) {
            $location.url(path);
        };
        $rootScope.go_hard = function (full_path) {
            $window.location.href = full_path;
        };
        $rootScope.reload = function () {
            $window.location.reload();
        };

        $scope.logout = function () {
            Object.keys($sessionStorage.loginResponse).forEach(function (key) {
                delete $sessionStorage.loginResponse[key];
            });
            $rootScope.isLoggedIn = false;

            $rootScope.go('/login');
        };

        $scope.viewNotificationList = function () {
            $rootScope.go('/history')
        }

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
