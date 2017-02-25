/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {

    function LoginController($scope, HttpFactory) {
        $scope.submitLogin = function () {
            HttpFactory.login($scope.user.email, $scope.user.password).then(function (loginResponse) {
                
            })
        }
    }

    var app = angular.module('CRNS'),
        requires = [
            '$scope',
            'modules.core.HttpFactory',
            LoginController
        ];
    app.controller('LoginController', requires)
}());
