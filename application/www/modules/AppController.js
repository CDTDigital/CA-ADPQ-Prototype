'use strict';
angular.module('CRNSSrv')
.controller('AppCtrl', ['$scope', 'AuthToken', '$state', function($scope, AuthToken, $state) {
    /* Side Menu Navigation */
    $scope.onTapSideMenu = function(type) {
        // TO DO
        if(type == 'logout') {
            AuthToken.removeAuthItems();
            $state.go('login');
        }
    };
}])
.controller('DashboardCtrl', [function() {
    // To D0
}]);
