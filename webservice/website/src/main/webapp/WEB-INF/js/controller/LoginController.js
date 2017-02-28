/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {

    function LoginController($scope, $rootScope, HttpFactory, toaster) {
        function onLoad() {
            if (HttpFactory.isLoggedIn()) {
                $scope.go('/account')
            }
        }

        $scope.submitLogin = function () {
            $scope.isLoading = true;
            HttpFactory.login($scope.user.email, $scope.user.password).then(function (loginResponse) {
                //$rootScope.isLoggedIn = true;
                //$rootScope.user_role = "USER";
                $scope.go('/account')
            }, function (error) {
                $scope.isLoading = false;
                toaster.pop('error', error.message);
            })
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
