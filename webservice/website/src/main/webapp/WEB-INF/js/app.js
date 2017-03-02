/**
 * Created by sunil.jhamnani on 2/21/17.
 */

(function () {
    angular.module("CRNS", [
        //3rd party module
        'ngRoute',
        'ngStorage',
        'toaster',
        'ngAnimate',
        'ui.bootstrap'
    ]);
}());
(function () {
    'use strict';
    function config($routeProvider, $httpProvider, Configuration, HttpFactoryProvider) {
        $routeProvider.when('/', {
            templateUrl: '/view/auth/login.html',
            controller: "LoginController"
        }).when('/login', {
            templateUrl: '/view/auth/login.html',
            controller: "LoginController"
        }).when('/register', {
            templateUrl: '/view/auth/registration.html',
            controller: 'RegistrationController'
        }).when('/home', {
            templateUrl: '/view/auth/home.html',
            controller: "HomeController"
        }).when('/history', {
            templateUrl: '/view/notification/history.html',
            controller: "HistoryController"
        }).when('/message', {
            templateUrl: '/view/notification/message.html',
            controller: "MessageController"
        }).when('/usersList', {
            templateUrl: '/view/user/list.html',
            controller: "UserListController"
        }).when('/account', {
            templateUrl: '/view/user/account.html',
            controller: "AccountController"
        }).otherwise({
            redirectTo: '/login'
        });

        $httpProvider.interceptors.push(function ($q, $rootScope, $sessionStorage) {
            return {
                responseError: function (rejection) {
                    if(rejection.data.statusCode == 401 || rejection.data.statusCode == 402) {
                        console.log("rejected credentials");
                        Object.keys($sessionStorage.loginResponse).forEach(function (key) {
                            delete $sessionStorage.loginResponse[key];
                        });
                        $rootScope.isLoggedIn = false;
                        $rootScope.go('/login');
                    }
                }
            }

        });

        $httpProvider.defaults.withCredentials = true;
        HttpFactoryProvider.config({
            baseUrl: Configuration.API_URL
        })
    }

    function run($rootScope, $sessionStorage, $http) {
        $rootScope.$on('$locationChangeStart', function () {
            console.log("in locationChangeStart");
            console.log(Object.keys($sessionStorage.loginResponse).length);
            if (Object.keys($sessionStorage.loginResponse).length > 0) {

                $rootScope.isLoggedIn = true;
                console.log($rootScope.isLoggedIn);
                $rootScope.loginResponse = $sessionStorage.loginResponse
                //$http.defaults.headers.common.cookie
            }
        })
    }

    var app = angular.module("CRNS"),
        requires = [
            '$routeProvider',
            '$httpProvider',
            'Configuration',
            'modules.core.HttpFactoryProvider',
            config
        ],
        runRequires = [
            '$rootScope',
            '$sessionStorage',
            '$http',
            run
        ];
    app.config(requires);
    app.run(runRequires);
}());