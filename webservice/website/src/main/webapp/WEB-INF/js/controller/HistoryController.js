/**
 * Created by sunil.jhamnani on 2/26/17.
 */

(function () {
    'use strict';

    /**
     * HistoryController responsible for view logic of history.html to list now all
     * the notifications for User/Admin
     * @param $scope
     * @param $filter
     * @param HttpFactory
     * @constructor
     */
    function HistoryController($scope, $filter, HttpFactory) {

        /**
         * HistoryController initialization function
         */
        function onInit() {
            $scope.role = HttpFactory.getUserRole();
            HttpFactory.getNotificationList().then(function (list) {
                $scope.notificationList = list;
            });
            $scope.role = HttpFactory.getUserRole();
            //var not = $filter('filternot')($scope.notificationList, "push");
        }
        onInit()
    }

    var app = angular.module("CRNS"),
        requires = [
            '$scope',
            '$filter',
            'modules.core.HttpFactory',
            HistoryController
        ];
    app.controller('HistoryController', requires);
}());
