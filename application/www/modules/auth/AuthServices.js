angular.module('CRNSSrv')
    .service('AuthToken', ['$http', function($http) {
        this.setAuthToken = function(token) {
            if(token != undefined && token != null) {
                $http.defaults.headers.common['authToken']= token;
                localStorage.setItem('authToken', token);
            }
        };

        this.getAuthToken = function() {
            return localStorage.getItem('authToken');
        };

        this.removeAuthItems = function() {
            localStorage.removeItem('authToken');
            localStorage.removeItem('loginData');
            localStorage.removeItem('accountSetup');
            $http.defaults.headers.common['authToken']= null;
        };
    }])
    .factory('AuthServices', ['$rootScope', '$http', 'Constant', 'AuthToken', function($rootScope, $http, Constant, AuthToken) {
        return {
            login: function(paramObj) {
                $rootScope.$broadcast('httpCallStarted');
                var promise = $http.post(Constant.API_URL + 'login', paramObj)
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        AuthToken.setAuthToken(data.data.authToken);
                        return data;
                    }, function(data, status, headers, config) {
                        return data;
                    });
                return promise;
            },
            register: function(paramObj) {
                $rootScope.$broadcast('httpCallStarted');
                var promise = $http.post(Constant.API_URL + 'users/createUser', paramObj)
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        return data;
                    }, function(data, status, headers, config) {
                        return data;
                    });
                return promise;
            }
        };
    }]);
