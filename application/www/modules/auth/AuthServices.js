angular.module('CRNSSrv')
    .service('AuthToken', ['$http', 'AccountData', function($http, AccountData) {
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
            localStorage.removeItem('locationData');
            AccountData.clearProfileSetup();
            $http.defaults.headers.common['authToken']= null;
        };
    }])
    .factory('AuthServices', ['$rootScope', '$http', 'Constant', 'AuthToken', '$q', function($rootScope, $http, Constant, AuthToken, $q) {
        return {
            login: function(paramObj) {
                $rootScope.$broadcast('httpCallStarted');
                var defered = $q.defer();
                $http.post(Constant.API_URL + 'login', paramObj)
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        AuthToken.setAuthToken(data.data.authToken);
                        return defered.resolve(data);
                    }, function(data, status, headers, config) {
                        return defered.reject(data);
                 });
                return defered.promise;
            },
            logout: function() {
                $rootScope.$broadcast('httpCallStarted');
                var defered = $q.defer();
                $http.get(Constant.API_URL + 'logout')
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        AuthToken.removeAuthItems();
                        return defered.resolve(data);
                    }, function(data, status, headers, config) {
                        return defered.reject(data);
                 });
                return defered.promise;
            },
            register: function(paramObj) {
                $rootScope.$broadcast('httpCallStarted');
                var defered = $q.defer();
                $http.post(Constant.API_URL + 'users/createUser', paramObj)
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        return defered.resolve(data);
                    }, function(data, status, headers, config) {
                        return defered.reject(data);
                });
                return defered.promise;
            }
        };
    }]);
