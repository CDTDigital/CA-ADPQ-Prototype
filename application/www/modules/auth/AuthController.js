angular.module('CRNSCtrl')
.controller('AuthCtrl', ['$rootScope', '$scope', '$ionicScrollDelegate', '$state', 'AuthServices', 'Notify', 'Constant', 'DeviceService', function($rootScope, $scope, $ionicScrollDelegate, $state, AuthServices, Notify, Constant, DeviceService) {
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
        } else if(!Constant.IS_EMAIL.test($scope.register.email)) {
            Notify.errorToaster('Email address is not valid!');
        } else if($scope.register.password != $scope.register.cpassword) {
            Notify.errorToaster('Password and Confirm Password are mismatch!');
        } else {
            AuthServices.register($scope.register).then(function(resp) {
                console.log(resp);
                if(resp.data && resp.data.responseStatus == 'SUCCESS') {
                    Notify.successToaster('Registration Successful!');
                    $state.go('login');
                } else {
                    Notify.errorToaster(resp.data.data.message);
                };
            });
        }
    };

    $scope.backToLogin = function() {
        $state.go('login');
    };

    // Perform the login action when the user submits the login form
    $scope.onTapLoginBtn = function() {
        if($scope.login.userName == '' || $scope.login.password == '') {
            Notify.errorToaster('Please fill Username and Password!');
        } else {
            var tmpDeviceInfo = DeviceService.getDeviceInfo();
            $scope.login.deviceId = tmpDeviceInfo.deviceId;
            $scope.login.deviceType = tmpDeviceInfo.deviceType;
            $scope.login.deviceToken = tmpDeviceInfo.deviceToken;
            AuthServices.login($scope.login).then(function(resp) {
                if (resp.data && resp.data.responseStatus == 'SUCCESS') {
                    var tempObj = {
                        email: resp.data.data.email,
                        userName: resp.data.data.userName,
                        authToken: resp.data.authToken,
                        accountSetupDone: resp.data.data.accountSetupDone,
                    };

                    $rootScope.loginData = tempObj;
                    window.localStorage.setItem('loginData', angular.toJson($rootScope.loginData));
                    if (!$rootScope.loginData.accountSetupDone) $state.go('accountSetup');
                    else $state.go('app.dash');
                } else {
                    Notify.errorToaster('Incorrect Username or Password!');
                };
            });
        }
    };

    $scope.resetModels();
}]);
