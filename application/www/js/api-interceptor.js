// Intercepting HTTP calls with AngularJS.
angular.module('CRNSInterceptor')
    .config(function($provide, $httpProvider) {
        // Intercept http calls.
        $provide.factory('CRNSInterceptor', ['$q', '$rootScope', 'Constant', function($q, $rootScope, Constant) {
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
                    $rootScope.$broadcast('httpCallCompleted');
                    var defer = $q.defer();
                    console.log(rejection);
                    if (rejection.status == 0 || rejection.status == -1) {
                        if (Constant.IsNetworkAlive) {
                            $rootScope.tempErrorHandler('Error', 'Remote services required by the app are temporarily unavailable, please try again later.', 'Ok', undefined);
                        } else {
                            $rootScope.tempErrorHandler('Error', 'Your are not connected to the network!', 'Ok', undefined);
                        }
                    } else if (rejection.status == 401) {
                        // $rootScope.tempErrorHandler('Error', 'Your authentication session is expired. Please logout the app.', 'Ok', 'login');
                    }

                    defer.reject(rejection);
                    return defer.promise;
                },
            };
        }]);

        // Add the interceptor to the $httpProvider.
        $httpProvider.interceptors.push('CRNSInterceptor');
    });
