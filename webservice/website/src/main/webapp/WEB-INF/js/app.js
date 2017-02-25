/**
 * Created by sunil.jhamnani on 2/21/17.
 */

(function () {
    angular.module("CRNS", [
        //3rd party module
        'ngRoute'
    ]);
}());
(function () {
    'use strict';
    function config($routeProvider, Configuration, HttpFactoryProvider) {
        $routeProvider.when('/', {
            templateUrl: '/view/auth/login.html',
            controller: "LoginController"
        }).when('/login', {
            templateUrl: '/view/auth/login.html',
            controller: "LoginController"
        }).when('/home', {
            templateUrl: '/view/auth/home.html',
            controller: "HomeController"
        }).when('/history', {
            templateUrl: '/view/notification/history.html',
            controller: "HistoryController"
        }).when('/message', {
            templateUrl: '/view/notification/message.html',
            controller: "MessageController"
        }).when('/users', {
            templateUrl: '/view/user/list.html',
            controller: "UserController"
        }).otherwise({
            redirectTo: '/login'
        });

        HttpFactoryProvider.config({
            baseUrl: Configuration.API_URL
        })
    }

    var app = angular.module("CRNS"),
        requires = [
            '$routeProvider',
            'Configuration',
            'modules.core.HttpFactoryProvider',
            config
        ];
    app.config(requires);
}());