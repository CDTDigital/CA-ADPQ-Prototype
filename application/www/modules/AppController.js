'use strict';
angular.module('CRNSCtrl')
.controller('AppCtrl', ['$scope', 'Notify', '$state', '$ionicSideMenuDelegate', function($scope, Notify, $state, $ionicSideMenuDelegate) {
    /* Toggle Left for Side Menu */
    $scope.toggleLeft = function() {
        $ionicSideMenuDelegate.toggleLeft();
    };

    /* Side Menu Navigation */
    $scope.onTapSideMenu = function(type) {
        // TO DO
        if(type == 'logout') {
            Notify.errorConfirm('Confirm', 'Are you sure you want to sign out?', ['Cancel', 'Ok'], 'logout');
        } else if(type == 'settings') {
            $state.go('app.settings');
        } else if(type == 'notification') {
            $state.go('app.list');
        } else if(type == 'profile') {
            $state.go('app.profile');
        }
    };
}])
.controller('DashboardCtrl', [function() {
    // To D0
}]);
