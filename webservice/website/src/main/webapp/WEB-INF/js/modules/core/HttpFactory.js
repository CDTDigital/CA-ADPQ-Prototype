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
            var notificationList = {};

            function login(email, password) {
                var deferred = $q.defer();
                var credentials = {userName: email, password: password};

                $http.post(self.baseUrl + '/login', credentials).then(function (response) {
                    console.log($http.defaults.headers.common.cookie);
                    if (response.data.responseStatus.toLowerCase() == "success") {
                        loginResponse = response.data;
                        //loginResponse.data.role = "ADMIN";
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
                $http.post(self.baseUrl + '/users/createUser', registerData).then(function (response) {
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
                $http.get(self.baseUrl + '/users/' + id).then(function (response) {
                    deferred.resolve(response.data.data)
                }, function (error) {
                    deferred.reject(error);
                });

                return deferred.promise;
            }

            function setUserProfile(updatedData) {
                var deferred = $q.defer();
                var id = $sessionStorage.loginResponse.data.id;
                $http.post(self.baseUrl + '/users/' + id, updatedData).then(function (response) {
                    deferred.resolve(response.data.data)
                }, function (error) {
                    deferred.reject(error);
                });

                return deferred.promise;
            }

            function getNotificationList() {
                var deferred = $q.defer();
                var id = $sessionStorage.loginResponse.data.id;
                queryUrl = $sessionStorage.loginResponse.data.role == "USER" ? self.baseUrl + '/notifications/userNotifications/' + id : self.baseUrl + "/notifications/list";
                $http.get(queryUrl).then(function (response) {
                    deferred.resolve(response.data.data);
                    //notificationList = response.data.data;
                }, function (error) {
                    deferred.reject(error);
                });

                return deferred.promise;
            }

            function pushNotification(notificationData) {
                var deferred = $q.defer();
                $http.post(self.baseUrl + '/notifications/send', notificationData).then(function (response) {
                    deferred.resolve(response.data)
                }, function () {
                    deferred.reject(error)
                });

                return deferred.promise;
            }

            function getUserRole() {
                return $sessionStorage.loginResponse.data.role;
            }

            function getUserId() {
                return $sessionStorage.loginResponse.data.id;
            }
            onInitialization();

            return {
                login: login,
                getUserRole: getUserRole,
                getUserId:getUserId,
                getNotificationList: getNotificationList,
                isLoggedIn: isLoggedIn,
                signup: signup,
                getUserProfile: getUserProfile,
                setUserProfile: setUserProfile,
                pushNotification: pushNotification
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