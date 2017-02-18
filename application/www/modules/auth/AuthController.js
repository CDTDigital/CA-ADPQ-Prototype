angular.module('CRNS')
.controller('AuthController', ['$scope', 'AuthServices', '$ionicModal', '$ionicScrollDelegate', function($scope, AuthServices, $ionicModal, $ionicScrollDelegate) {
    'use strict';

    // Create the registration modal that will use on tap on register link
    $ionicModal.fromTemplateUrl('views/registration.html', {
        scope: $scope,
        animation: 'slide-in-up',
        backdropClickToClose: false,
    }).then(function(modal) {
        $scope.registerModal = modal;
    });

    // Create the forgot modal that will use on tap on forgot link
    $ionicModal.fromTemplateUrl('views/forgot.html', {
        scope: $scope,
        animation: 'slide-in-up',
        backdropClickToClose: false,
    }).then(function(modal) {
        $scope.forgotModal = modal;
    });

    // Open the Forgot password modal
    $scope.onTapForgotLink = function() {
        $ionicScrollDelegate.scrollTop();
        $scope.forgot = {
            email: '',
        };

        $scope.forgotModal.show();
    };

    $scope.onRegisterCancel = function() {
        $scope.registerModal.hide();
    }

    $scope.onForgotCancel = function() {
        $scope.forgotModal.hide();
    }

    // Open the Register user modal
    $scope.onTapRegisterLink = function() {
        $ionicScrollDelegate.scrollTop();
        $scope.register = {
            firstName: '',
            lastName: '',
            email: '',
            phoneNumber: '',
            pasword: '',
            confirmPasword: '',
            isEmail: '',
            isSMS: '',
            isNotificaton: ''
        };

        $scope.registerModal.show();
    };

    // Perform the login action when the user submits the login form
    $scope.onTapRegisterSubmit = function() {
        console.log('Doing register', $scope.register);
    };
}]);
