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
    .factory('AuthServices', ['$http', 'Constant', 'AuthToken', function($http, Constant, AuthToken) {
        return {
            login: function(paramObj) {
                var promise = $http.post(Constant.API_URL + 'login', paramObj)
                    .then(function(data, status, headers, config) {
                        AuthToken.setAuthToken(data.data.authToken);
                        return data;
                    });
                return promise;
            },
            register: function(paramObj) {
                var promise = $http.post(Constant.API_URL + 'register', paramObj)
                    .then(function(data, status, headers, config) {
                        return data;
                    });
                return promise;
            }
        };
    }]);
