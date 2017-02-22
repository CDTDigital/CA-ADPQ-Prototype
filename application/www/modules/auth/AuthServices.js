angular.module('CRNSSrv')
.factory('AuthServices', ['$http', function($http) {
    return {
        login: function(paramObj) {
            var promise = $http.post(rootUrl + '/login.json', paramObj)
                .success(function(data, status, headers, config) {
                    return data;
                });
            return promise;
        }
    };
}]);
