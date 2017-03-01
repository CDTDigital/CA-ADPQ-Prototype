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

        //$rootScope.changeLoginStatus = function() {
        //    $scope.isLoggedIn = true
        //};

        $scope.logout = function () {
            Object.keys($sessionStorage.loginResponse).forEach(function (key) {
                delete $sessionStorage.loginResponse[key];
            });
            $rootScope.isLoggedIn = false;
            $rootScope.go('/login');
        };
        //$rootScope.$on('$locationChangeStart', function () {
        //    if (Object.keys($sessionStorage.loginResponse).length > 0) {
        //        $rootScope.isLoggedIn = true;
        //        $rootScope.loginResponse = $sessionStorage.loginResponse
        //    }
        //})

        $scope.viewNotificationList = function () {
            $rootScope.go('/history')
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
