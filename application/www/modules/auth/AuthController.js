angular.module('CRNSCtrl')
.controller('AuthCtrl', ['$scope', '$ionicScrollDelegate', '$state', 'AuthServices', 'Notify', 'Constant', function($scope, $ionicScrollDelegate, $state, AuthServices, Notify, Constant) {
    'use strict';
    // Open the Forgot password modal
    $scope.resetModels = function() {
        $scope.login = {
            userName: '',
            password: ''
        };

        $scope.register = {
            userName: '',
            email: '',
            password: '',
            cpassword: ''
        };

        $scope.forgot = {
            email: ''
        };
    };

    $scope.onTapForgotLink = function() {
        $ionicScrollDelegate.scrollTop();
        $scope.resetModels();
        $state.go('forgot');
    };

    // Perform the register action when the user submits the register form
    $scope.onTapResetBtn = function() {
        console.log('Doing register', $scope.forgot);
    };

    // Open the Register user modal
    $scope.onTapRegisterLink = function() {
        $ionicScrollDelegate.scrollTop();
        $scope.resetModels();
        $state.go('register');
    };

    // Perform the register action when the user submits the register form
    $scope.onTapRegisterBtn = function() {
        console.log('Doing register', $scope.register);
        if($scope.register.userName == '' || $scope.register.email == '' || $scope.register.password == '' || $scope.register.cpassword == '') {
            Notify.errorToaster('Please fill all the input fields!');
        } else if(!Constant.isEmail.test($scope.register.email)) {
            Notify.errorToaster('Email address is not valid!');
        } else if($scope.register.password != $scope.register.cpassword) {
            Notify.errorToaster('Password and Confirm Password are mismatch!');
        } else {
            AuthServices.register($scope.register).then(function(resp) {
                if(resp.data && resp.data.responseStatus == 'SUCCESS') {
                    Notify.successToaster(resp.data.message);
                    $state.go('login');
                } else {
                    Notify.successToaster(resp.data.message);
                };
            });
        }
    };

    $scope.backToLogin = function() {
        $state.go('login');
    };

    // Perform the login action when the user submits the login form
    $scope.onTapLoginBtn = function() {
        AuthServices.login($scope.login).then(function(resp) {
            if(resp.data && resp.data.responseStatus == 'SUCCESS') {
                $state.go('accountSetup');
            } else {
                Notify.errorToaster(resp.data.message);
            };
        });
    };

    $scope.resetModels();
}]);
