/**
 * Created by sunil.jhamnani on 2/26/17.
 */

(function () {
    'use strict';

    function HistoryController($scope, $filter, HttpFactory) {
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
