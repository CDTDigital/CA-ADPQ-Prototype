angular.module('CRNSSrv')
    .factory('AuthServices', ['$http', 'Constant', function($http, Constant) {
        return {
            login: function(paramObj) {
                var promise = $http.post(Constant.API_URL + 'login', paramObj)
                    .then(function(data, status, headers, config) {
                        $http.defaults.headers.common['authToken']= data.data.authToken;
                        localStorage.setItem('authToken', data.data.authToken);
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
