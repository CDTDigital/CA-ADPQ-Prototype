'use strict';
angular.module('CRNSCtrl')
.controller('AppCtrl', ['$scope', 'AuthToken', '$state', '$ionicSideMenuDelegate', function($scope, AuthToken, $state, $ionicSideMenuDelegate) {
    /* Toggle Left for Side Menu */
    $scope.toggleLeft = function() {
        $ionicSideMenuDelegate.toggleLeft();
    };

    /* Side Menu Navigation */
    $scope.onTapSideMenu = function(type) {
        // TO DO
        if(type == 'logout') {
            AuthToken.removeAuthItems();
            $state.go('login');
        } else if(type == 'settings') {
            $state.go('app.settings');
        } else if(type == 'notification') {
            $state.go('app.list');
        }
    };
}])
.controller('DashboardCtrl', [function() {
    // To D0
}]);
