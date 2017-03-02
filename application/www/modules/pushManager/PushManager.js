'use strict';
(function() {
    angular.module('CRNSPushManager')
        .service('DeviceService', [function() {
            this.setDeviceInfo = function(deviceToken, deviceUUID) {
                var deviceInfo = {
                    deviceToken: deviceToken,
                    deviceType: ionic.Platform.platform(),
                    deviceId: deviceUUID
                };
                window.localStorage.setItem('deviceInfo', angular.toJson(deviceInfo));
            };

            this.getDeviceInfo = function() {
                return angular.fromJson(localStorage.getItem('deviceInfo'));
            };

            this.getDeviceToken = function() {
                return angular.fromJson(localStorage.getItem('deviceInfo')).deviceToken;
            };
        }])
        .factory('PushNotificationService', ['$rootScope', '$q', 'DeviceService', function($rootScope, $q, DeviceService) {
            var push;
            return {
                init: function() {
                    var q = $q.defer();
                    push = PushNotification.init({
                        'android': {
                            'senderID': '450574513627',
                            'icon': 'icon',
                            'iconColor': '#cccccc',
                            'sound': true,
                            'vibrate': 'false'
                        },
                        'ios': {
                            'senderID': '450574513627',
                            'gcmSandbox': true,
                            'alert': 'true',
                            'badge': 'true',
                            'sound': 'true',
                            'clearBadge': 'true'
                        }
                    });

                    q.resolve(push);
                    return q.promise;
                },
                register: function() {
                    $rootScope.$broadcast('httpCallStarted');
                    push.on('registration', function(data) {
                        $rootScope.$broadcast('httpCallCompleted');
                        DeviceService.setDeviceInfo(data.registrationId, device.uuid);
                    });
                },

                notification: function() {
                    push.on('notification', function(data) {
                        console.log(data);
                        var payloadObj = {};
                        payloadObj.data = {};
                        payloadObj.data.message = data.message;
                        payloadObj.data.title = data.title;
                        payloadObj.data.notificationId = data.additionalData.notificationId;
                        payloadObj.foreground = data.additionalData.foreground;
                        $rootScope.onCRNSNotification(payloadObj.data, payloadObj.foreground);
                    });
                },

                error: function() {
                    push.on('error', function(e) {
                        console.log(e.message);
                    });
                },

                unregister: function() {
                    push.unregister(function() {
                        console.log('success');
                    }, function() {
                        console.log('error');
                    });
                }
            };
        }]);
}());
