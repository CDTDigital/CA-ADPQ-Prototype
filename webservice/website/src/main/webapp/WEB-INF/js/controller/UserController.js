/**
 * Created by sunil.jhamnani on 3/1/17.
 */

(function () {
    'use strict';

    function UserController($scope, $filter, toaster, HttpFactory, GooglePlacesFactory) {
        function onInit() {
            HttpFactory.getUserList().then(function (list) {
                $scope.userList = list;
            }, function (error) {
                toaster.pop('error', "Error while fetching user list")
            })
        }

        onInit();
    }

    var app = angular.module("CRNS"),
        requires = [
            '$scope',
            '$filter',
            'toaster',
            'modules.core.HttpFactory',
            'modules.google.GooglePlacesFactory',
            UserController
        ];
    app.controller('UserController', requires);
}());
