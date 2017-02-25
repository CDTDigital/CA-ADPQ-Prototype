/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {

    function LoginController($scope, HttpFactory, toaster) {
        function onLoad() {
            if (HttpFactory.isLoggedIn()) {
                $scope.go('/home')
            }
        }

        $scope.submitLogin = function () {
            HttpFactory.login($scope.user.email, $scope.user.password).then(function (loginResponse) {
                $scope.go('/home')
            }, function (error) {
                toaster.pop('info', error.message);
            })
        }
        onLoad();
    }

    var app = angular.module('CRNS'),
        requires = [
            '$scope',
            'modules.core.HttpFactory',
            'toaster',
            LoginController
        ];
    app.controller('LoginController', requires)
}());
