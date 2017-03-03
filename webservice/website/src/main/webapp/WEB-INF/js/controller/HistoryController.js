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
    function HistoryController($scope, HttpFactory) {

        /**
         * HistoryController initialization function
         */
        function onInit() {
            $scope.isLoading = true;
            $scope.role = HttpFactory.getUserRole();
            HttpFactory.getNotificationList().then(function (list) {
                $scope.notificationList = list;
                $scope.isLoading = false;
            }, function (error) {
                $scope.isLoading = false;
            });
            $scope.role = HttpFactory.getUserRole();
            //var not = $filter('filternot')($scope.notificationList, "push");
        }
        $scope.$watch('notificationList', function(){
            console.log($scope.notificationList);
        });
        onInit()
    }

    var app = angular.module("CRNS"),
        requires = [
            '$scope',
            'modules.core.HttpFactory',
            HistoryController
        ];
    app.controller('HistoryController', requires);
}());
