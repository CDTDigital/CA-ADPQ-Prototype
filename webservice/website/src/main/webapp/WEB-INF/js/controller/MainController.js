/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {

    function MainController($rootScope, $location) {
        $rootScope.go = function (path) {
            $location.url(path);
        }
    }

    var app = angular.module("CRNS"),
        requires = [
            '$rootScope',
            '$location',
            MainController
        ];
    app.controller("MainController", requires)
}());
