angular.module('CRNS')
.factory('AuthServices', ['$http', '$q', function($http, $q) {
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
