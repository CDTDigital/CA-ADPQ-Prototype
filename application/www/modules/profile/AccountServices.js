angular.module('CRNSSrv')
    .service('AccountData', [function() {
        var accountData = {
            firstName: '',
            lastName: '',
            mobileNo: '',
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

        this.setCurrentData = function(data) {
            accountData = data;
        };

        this.getCurrentData = function(msg) {
            return accountData;
        };
    }])
    .factory('AccountServices', ['$rootScope', '$http', 'Constant', function($rootScope, $http, Constant) {
        return {
            setUpAccount: function(paramObj) {
                $rootScope.$broadcast('httpCallStarted');
                var promise = $http.post(Constant.API_URL + 'users/setProfile', paramObj)
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        return data;
                    }, function(data, status, headers, config) {
                        return data;
                    });
                return promise;
            }
        };
    }]);
