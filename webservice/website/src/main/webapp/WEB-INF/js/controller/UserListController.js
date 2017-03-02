/**
 * Created by sunil.jhamnani on 3/1/17.
 */

(function () {
    'use strict';

    /**
     * UserListController responsible for view logic of list.html listing all the users. Only accessible to ADMIN
     * @param $scope
     * @param $filter
     * @param toaster
     * @param HttpFactory
     * @param GooglePlacesFactory
     * @constructor
     */
    function UserListController($scope, $filter, toaster, HttpFactory, GooglePlacesFactory) {

        /**
         * UserListController initialization function
         */
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
            UserListController
        ];
    app.controller('UserListController', requires);
}());
