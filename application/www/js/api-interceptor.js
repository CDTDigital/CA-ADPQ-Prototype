/**
 * Created by Harish Patidar on 2/16/17.
 */
// Intercepting HTTP calls with AngularJS.
angular.module('CRNS.Interceptor', [])
    .config(function($provide, $httpProvider) {
        // Intercept http calls.
        $provide.factory('CRNSInterceptor', function($q, $rootScope) {
            return {
                // On request success
                request: function(config) {
                    // Return the config or wrap it in a promise if blank.
                    return config || $q.when(config);
                },

                // On request failure
                requestError: function(rejection) {
                    // Return the promise rejection.
                    return $q.reject(rejection);
                },

                // On response success
                response: function(response) {
                    // Return the response or promise.
                    return response || $q.when(response);
                },

                // On response failture
                responseError: function(rejection) {
                    var defer = $q.defer();
                    console.log(rejection);

                    // TO DO

                    defer.reject(rejection);
                    return defer.promise;
                },
            };
        });

        // Add the interceptor to the $httpProvider.
        $httpProvider.interceptors.push('CRNSInterceptor');
    });
