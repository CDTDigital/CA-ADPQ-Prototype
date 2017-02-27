/**
 * Created by sunil.jhamnani on 2/24/17.
 */

(function () {
    function HttpProvider() {
        var self = this;
        self.config = function (config) {
            angular.extend(self, config);
        };

        function HttpFactory($http, $log, $q, $sessionStorage) {

            function onInitialization() {
                $sessionStorage.$default({
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
                        $sessionStorage.loginResponse = loginResponse;
                        deferred.resolve(response.data)
                    }
                    else {
                        deferred.reject(response.data)
                    }
                }, function (error) {
                    deferred.reject(error.data.data);
                });
                return deferred.promise;
            }

            function isLoggedIn() {
                return Object.keys($sessionStorage.loginResponse).length;
            }

            function signup(registerData) {
                var deferred = $q.defer();
                $http.post('/users/createUser', registerData).then(function (response) {
                    if (response.data.responseStatus.toLowerCase() == "success"){
                        deferred.resolve();
                    }
                    else {
                        deferred.reject(response.data.data);
                    }
                }, function (error) {
                    deferred.reject(error);
                });

                return deferred.promise;
            }

            function getUserProfile() {
                var deferred = $q.defer();
                var id = $sessionStorage.loginResponse.data.id;
                $http.get('/users/' + id).then(function (response) {
                    deferred.resolve(response.data.data)
                }, function (error) {
                    deferred.reject(error);
                });

                return deferred.promise;
            }
            onInitialization();

            return {
                login: login,
                loginResponse: loginResponse,
                isLoggedIn: isLoggedIn,
                signup: signup,
                getUserProfile: getUserProfile
            }
        }

        self.$get = [
            '$http',
            '$log',
            '$q',
            '$sessionStorage',
            HttpFactory
        ];
    }
    var app = angular.module("CRNS");
    app.provider("modules.core.HttpFactory", HttpProvider);
}());