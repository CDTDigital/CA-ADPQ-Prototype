'use strict';
angular.module('CRNSMock').run(['$httpBackend', function($httpBackend) {
    var registerUserData = {users: []};
    if(localStorage.getItem('userData') == undefined) {
        localStorage.setItem('userData', angular.toJson(registerUserData));
    } else registerUserData = angular.fromJson(localStorage.getItem('userData'));

    $httpBackend.whenPOST(/.*\/mock.crns\/api\/login/).respond(function(method, url, data, headers) {
        data = angular.fromJson(data);
        for(var i=0; i < registerUserData.users.length; i++) {
            if(angular.fromJson(registerUserData.users[i]).userName == data.userName && angular.fromJson(registerUserData.users[i]).password == data.password) {
                return [200, { 'responseStatus': 'SUCCESS', 'authToken': 'hGJGY23Fdf42sJYd34', 'accountSetupDone': false}];
            }
        }
        return [200, { 'responseStatus': 'FAILURE', 'message': 'Incorrect Username or Password!'}];
    });

    $httpBackend.whenPOST(/.*\/mock.crns\/api\/register/).respond(function(method, url, data, headers) {
        registerUserData = angular.fromJson(localStorage.getItem('userData'));
        registerUserData.users.push(data);
        localStorage.setItem('userData', angular.toJson(registerUserData));
        return [200, { 'responseStatus': 'SUCCESS', 'message': 'Registration Successful' }];
    });

    $httpBackend.whenPOST(/.*\/mock.crns\/api\/setUpAccount/).respond(function(method, url, data, headers) {
        return [200, { 'responseStatus': 'SUCCESS' }];
    });

    // All other http requests will pass through
    $httpBackend.whenGET(/.*/).passThrough();
    $httpBackend.whenPOST(/.*/).passThrough();
    $httpBackend.whenDELETE(/.*/).passThrough();
    $httpBackend.whenPUT(/.*/).passThrough();
}]);
