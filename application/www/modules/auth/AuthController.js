angular.module('CRNSCtrl')
.controller('AuthCtrl', ['$scope', '$ionicModal', '$ionicScrollDelegate', '$state', function($scope, $ionicModal, $ionicScrollDelegate, $state) {
    'use strict';
    // Create the registration modal that will use on tap on register link
    $ionicModal.fromTemplateUrl('views/registration.html', {
        scope: $scope,
        animation: 'slide-in-up',
        backdropClickToClose: false
    }).then(function(modal) {
        $scope.registerModal = modal;
    });

    // Create the forgot modal that will use on tap on forgot link
    $ionicModal.fromTemplateUrl('views/forgot.html', {
        scope: $scope,
        animation: 'slide-in-up',
        backdropClickToClose: false
    }).then(function(modal) {
        $scope.forgotModal = modal;
    });

    // Open the Forgot password modal
    $scope.onTapForgotLink = function() {
        $ionicScrollDelegate.scrollTop();
        $scope.forgot = {
            email: ''
        };

        $scope.forgotModal.show();
    };

    // Perform the register action when the user submits the register form
    $scope.onTapResetBtn = function() {
        console.log('Doing register', $scope.forgot);
    };

    $scope.onForgotCancel = function() {
        $scope.forgotModal.hide();
    };

    // Open the Register user modal
    $scope.onTapRegisterLink = function() {
        $ionicScrollDelegate.scrollTop();
        $scope.register = {
            userName: '',
            email: '',
            pasword: ''
        };

        $scope.registerModal.show();
    };

    // Perform the register action when the user submits the register form
    $scope.onTapRegisterBtn = function() {
        console.log('Doing register', $scope.register);
    };

    $scope.onRegisterCancel = function() {
        $scope.registerModal.hide();
    };

    $scope.login = {
        userName: '',
        password: ''
    };

    // Perform the login action when the user submits the login form
    $scope.onTapLoginBtn = function() {
        $state.go('accountSetup');
    };
}]);
