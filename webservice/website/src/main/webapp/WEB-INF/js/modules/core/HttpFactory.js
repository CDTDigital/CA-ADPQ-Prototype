/**
 * Created by sunil.jhamnani on 2/24/17.
 */

(function () {
    'use strict';

    function HttpProvider() {
        var self = this;
        self.config = function (config) {
            angular.extend(self, config);
        };

        /**
         * Service function. It exposes tjhe api to be used anywhere in the application
         * @param $http
         * @param $log
         * @param $q
         * @param $sessionStorage
         * @returns {{login: login, getUserRole: getUserRole, getUserId: getUserId, getNotificationList: getNotificationList, isLoggedIn: isLoggedIn, signup: signup, getUserProfile: getUserProfile, setUserProfile: setUserProfile, pushNotification: pushNotification, getUserList: getUserList}}
         * @constructor
         */
        function HttpFactory($http, $log, $q, $sessionStorage) {

            /**
             * Intialization function
             */
            function onInitialization() {
                $sessionStorage.$default({
                    loginResponse: {}
                })
            }

            var loginResponse = {};
            var notificationList = {};

            /**
             * Send post request to api for logging in
             * @param email
             * @param password
             * @returns {*|r.promise|promise|d.promise}
             */
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
                        $log.debug(response.data);
                        deferred.reject(response.data)
                    }
                }, function (error) {
                    $log.debug(error);
                    deferred.reject(error.data.data);
                });
                return deferred.promise;
            }

            /**
             * Get a true flag if the used is logged in
             * @returns {Number}
             */
            function isLoggedIn() {
                return Object.keys($sessionStorage.loginResponse).length;
            }

            /**
             * Post request to create new user
             * @param registerData
             * @returns {*|r.promise|promise|d.promise}
             */
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
                    $log.debug(error);
                    deferred.reject(error);
                });

                return deferred.promise;
            }

            /**
             * Get request for fetching user/admin details
             * @returns {*|r.promise|promise|d.promise}
             */
            function getUserProfile() {
                var deferred = $q.defer();
                var id = $sessionStorage.loginResponse.data.id;
                $http.get(self.baseUrl + '/users/' + id).then(function (response) {
                    deferred.resolve(response.data.data)
                }, function (error) {
                    $log.debug(error);
                    deferred.reject(error);
                });

                return deferred.promise;
            }

            /**
             * Post request to change details of user/admin
             * @param updatedData
             * @returns {*|r.promise|promise|d.promise}
             */
            function setUserProfile(updatedData) {
                var deferred = $q.defer();
                var id = $sessionStorage.loginResponse.data.id;
                $http.post(self.baseUrl + '/users/' + id, updatedData).then(function (response) {
                    deferred.resolve(response.data.data)
                }, function (error) {
                    $log.debug(error);
                    deferred.reject(error);
                });

                return deferred.promise;
            }

            /**
             * Get the Notication list based on user role
             * @returns {*|r.promise|promise|d.promise}
             */
            function getNotificationList() {
                var deferred = $q.defer();
                var id = $sessionStorage.loginResponse.data.id;
                var queryUrl = $sessionStorage.loginResponse.data.role == "USER" ? self.baseUrl + '/notifications/userNotifications/' + id : self.baseUrl + "/notifications/list";
                $http.get(queryUrl).then(function (response) {
                    deferred.resolve(response.data.data);
                    //notificationList = response.data.data;
                }, function (error) {
                    $log.debug(error);
                    deferred.reject(error);
                });

                return deferred.promise;
            }

            /**
             * Push new notifcation to server
             * @param notificationData
             * @returns {*|r.promise|promise|d.promise}
             */
            function pushNotification(notificationData) {
                var deferred = $q.defer();
                $http.post(self.baseUrl + '/notifications/send', notificationData).then(function (response) {
                    deferred.resolve(response.data)
                }, function () {
                    $log.debug(error);
                    deferred.reject(error)
                });

                return deferred.promise;
            }

            /**
             * Get the complete user list for Admin
             * @returns {*|r.promise|promise|d.promise}
             */
            function getUserList() {
                var deferred = $q.defer();
                $http.get(self.baseUrl + '/users/list').then(function (response) {
                    deferred.resolve(response.data);
                }, function (error) {
                    $log.debug(error);
                    deferred.reject(error)
                });

                return deferred.promise;
            }

            function logoutUser() {
                var deferred = $q.defer();
                $http.get(self.baseUrl + '/logout').then(function (response) {
                    deferred.resolve(response.data);
                }, function (error) {
                    $log.debug(error);
                    deferred.reject(error)
                });

                return deferred.promise;
            }

            /**
             * Getter to get user role
             * @returns {*}
             */
            function getUserRole() {
                return $sessionStorage.loginResponse.data.role;
            }

            /**
             * Getter to get user id
             * @returns {*}
             */
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
                pushNotification: pushNotification,
                getUserList:getUserList,
                logoutUser:logoutUser
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