/**
 * Created by sunil.jhamnani on 2/21/17.
 */

(function () {
    'use strict';
    var app = angular.module("CRNS", ['ngRoute']);
    app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'views/auth/login.html',
            controller: "LoginController"
        }).when('/login', {
            templateUrl: 'views/auth/login.html',
            controller: "LoginController"
        }).when('/home', {
            templateUrl: 'views/auth/home.html',
            controller: "HomeController"
        }).when('/history', {
            templateUrl: 'views/notification/history.html',
            controller: "HistoryController"
        }).when('/message', {
            templateUrl: 'views/notification/message.html',
            controller: "MessageController"
        }).when('/users', {
            templateUrl: 'views/user/list.html',
            controller: "UserController"
        }).otherwise({
            redirectTo: '/login'
        });
    }])
}());