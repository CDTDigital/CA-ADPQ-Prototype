/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {
    function HomeController($scope, HttpFactory) {

    }

    var app = angular.module('CRNS'),
        requires = [
            '$scope',
            'modules.core.HttpFactory',
            HomeController
        ];
    app.controller('HomeController', requires)
}());