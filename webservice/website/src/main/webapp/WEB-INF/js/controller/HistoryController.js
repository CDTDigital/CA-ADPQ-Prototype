/**
 * Created by sunil.jhamnani on 2/26/17.
 */

(function () {
    function HistoryController($scope, HttpFactory) {
        function onInit() {
            $scope.notificationList = HttpFactory.notificationList;
            console.log($scope.notificationList)
        }
        onInit()
    }

    var app = angular.module("CRNS"),
        requires = [
            '$scope',
            'modules.code.HttpFactory',
            HistoryController
        ];
    app.controller('HistoryController', requires);
}());
