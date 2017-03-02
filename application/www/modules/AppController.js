'use strict';
angular.module('CRNSCtrl')
.controller('AppCtrl', ['$scope', 'AuthServices', '$state', '$ionicSideMenuDelegate', function($scope, AuthServices, $state, $ionicSideMenuDelegate) {
    /* Toggle Left for Side Menu */
    $scope.toggleLeft = function() {
        $ionicSideMenuDelegate.toggleLeft();
    };

    /* Side Menu Navigation */
    $scope.onTapSideMenu = function(type) {
        // TO DO
        if(type == 'logout') {
            AuthServices.logout().then(function(resp) {
                $state.go('login');
            }, function() {
            });
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
