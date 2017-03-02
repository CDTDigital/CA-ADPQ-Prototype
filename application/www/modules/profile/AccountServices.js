angular.module('CRNSSrv')
    .service('AccountData', ['GoogleMapService', function(GoogleMapService) {
        var accountData = {
            firstName: '',
            lastName: '',
            mobileNumber: '',
            location: {
                addressLine1: '',
                latitude: '',
                longitude: '',
                placeId: '',
                city: '',
                zipCode: ''
            },
            userNotificationOptions: {
                liveLocationTracking: false,
                sendEmail: true,
                sendSms: true,
                sendPushNotification: false
            }
        };

        var profileData = {
            firstName: '',
            lastName: '',
            mobileNumber: '',
            email: '',
            password: '',
            location: {
                addressLine1: '',
                latitude: '',
                longitude: '',
                placeId: '',
                city: '',
                zipCode: ''
            }
        };

        this.setCurrentData = function(data) {
            accountData = data;
        };

        this.getCurrentData = function(msg) {
            return accountData;
        };

        this.setUserProfileData = function(data) {
            profileData.firstName = data.firstName;
            profileData.lastName = data.lastName;
            profileData.mobileNumber = Number(data.mobileNo);
            profileData.email = data.email;
            profileData.password = (data.password == null || data.password == '') ? '*******' : data.password,
            profileData.location.addressLine1 = data.location.addressLine1;
            profileData.location.latitude = data.location.latitude;
            profileData.location.longitude = data.location.longitude;
            profileData.location.placeId = data.location.placeId;
            profileData.location.zipCode = data.location.zipCode;

            GoogleMapService.setLocationAddress({address: data.location.addressLine1, placeId: data.location.placeId, latitude: data.location.latitude, longitude: data.location.longitude, zipCode: data.location.zipCode});
        };

        this.getUserProfileData = function() {
            return profileData;
        };

        this.isProfileSetup = function() {
            return (profileData.firstName != '');
        };

        this.clearProfileSetup = function() {
            profileData.firstName = '';
        };
    }])
    .factory('AccountServices', ['$rootScope', '$http', 'Constant', '$q', 'AccountData', function($rootScope, $http, Constant, $q, AccountData) {
        return {
            setUpAccount: function(paramObj) {
                $rootScope.$broadcast('httpCallStarted');
                var defered = $q.defer();
                $http.post(Constant.API_URL + 'users/setProfile', paramObj)
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        return defered.resolve(data);
                    }, function(data, status, headers, config) {
                        return defered.reject(data);
                });
                return defered.promise;
            },
            getUserProfile: function() {
                $rootScope.$broadcast('httpCallStarted');
                var defered = $q.defer();
                $http.get(Constant.API_URL + 'users/getProfile')
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        AccountData.setUserProfileData(data.data.data);
                        return defered.resolve(data);
                    }, function(data, status, headers, config) {
                        return defered.reject(data);
                    });
                return defered.promise;
            },
            addCurrentLocation: function(paramObj) {
                $rootScope.$broadcast('httpCallStarted');
                var defered = $q.defer();
                $http.put(Constant.API_URL + 'users/setCurrentLocation', paramObj)
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
