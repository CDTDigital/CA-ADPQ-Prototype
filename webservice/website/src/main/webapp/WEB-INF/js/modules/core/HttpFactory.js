/**
 * Created by sunil.jhamnani on 2/24/17.
 */

(function () {
    function HttpProvider() {
        var self = this;
        self.config = function (config) {
            angular.extend(self, config);
        };

        function HttpFactory($http, $log, $q, $localStorage) {
            function onInitialization() {
                if (!self.baseUrl) {
                    $log.debug("Base Url is not provided in the angular constants");
                    return
                }

                $localStorage.$default({
                    loginResponse: {}
                })
            }

            var loginResponse = {};

            function login(email, password) {
                var deferred = $q.defer();
                var credentials = {userName: email, password: password}
                $http.post('/login', credentials).then(function (response) {
                    if (response.data.responseStatus.toLowerCase() == "success") {
                        loginResponse = response.data;
                        $localStorage.loginResponse = loginResponse;
                        deferred.resolve(response.data)
                    }
                    else {
                        deferred.reject(response.data)
                    }
                }, function (error) {
                    deferred.reject(error.data.data);
                })
                return deferred.promise;
            }
            return {
                login: login,
                loginResponse: loginResponse,
                forgotPassword: function () {
                    var deferred = $q.defer();
                    $http.get(Configuration.API_URL + '/forgotPassword').then(function (response) {
                        deferred.resolve();
                    }, function (error) {
                        deferred.reject(error);
                    })
                },

                signup: function () {
                    var deferred = $q.defer();
                    $http.get(Configuration.API_URL + '/signup').then(function (response) {
                        deferred.resolve();
                    }, function (error) {
                        deferred.reject(error);
                    })
                },

                createAdmin: function () {
                    var deferred = $q.defer();
                    $http.post(Configuration.API_URL + '/users/createAdmin').then(function (response) {
                        deferred.resolve();
                    }, function (error) {
                        deferred.reject(error);
                    })
                },

                createUser: function () {
                    var deferred = $q.defer();
                    $http.post(Configuration.API_URL + '/users/createUser').then(function (response) {
                        deferred.resolve();
                    }, function (error) {
                        deferred.reject(error);
                    })
                },

                isEmailRegistered: function () {
                    var deferred = $q.defer();
                    $http.get(Configuration.API_URL + '/users/isEmailRegistered').then(function (response) {
                        deferred.resolve();
                    }, function (error) {
                        deferred.reject(error);
                    })
                },

                isUniqueUsername: function () {
                    var deferred = $q.defer();
                    $http.get(Configuration.API_URL + '/users/isUniqueUsername').then(function (response) {
                        deferred.resolve();
                    }, function (error) {
                        deferred.reject(error);
                    })
                },

                userList: function () {
                    var deferred = $q.defer();
                    $http.get(Configuration.API_URL + '/users/list').then(function (response) {
                        deferred.resolve();
                    }, function (error) {
                        deferred.reject(error);
                    });
                }


            }
        }

        self.$get = [
            '$http',
            '$log',
            '$q',
            '$localStorage',
            HttpFactory
        ];
    }
    var app = angular.module("CRNS");
    app.provider("modules.core.HttpFactory", HttpProvider);
}());