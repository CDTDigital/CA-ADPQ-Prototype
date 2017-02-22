/**
 * Created by sunil.jhamnani on 2/21/17.
 */
(function () {
    angular.module("CRNS")
        .controller('MainController', ['$scope', '$rootScope', '$location', function ($scope, $rootScope, $location) {
            $rootScope.go = function (path) {
                $location.url(path);
            }
        }])
        .controller('LoginController', ['$scope', '$rootScope', function ($scope, $rootScope) {
            
        }])
        .controller('HomeController', ['$scope', '$rootScope', function ($scope, $rootScope) {
            
        }])
        .controller('HistoryController', ['$scope', '$rootScope', function ($scope, $rootScope) {

        }])
        .controller('MessageController', ['$scope', '$rootScope', function ($scope, $rootScope) {

        }])
        .controller('UserController', ['$scope', '$rootScope', function ($scope, $rootScope) {

        }])

}());