'use strict';
angular.module('CRNSCtrl')
    .controller('SettingsCtrl', ['$scope', 'SettingsServices', 'BgGeoLocation', function($scope, SettingsServices, BgGeoLocation) {
        var currentSettings = SettingsServices.getCurrentSettings().data;
        $scope.settings = {
            sendPushNotification: currentSettings.sendPushNotification,
            liveLocationTracking: currentSettings.liveLocationTracking,
            sendEmail: currentSettings.sendEmail,
            sendSms: currentSettings.sendSms
        };

        $scope.onChangeUpdate = function() {
            SettingsServices.setSettings($scope.settings);
        };

        $scope.toggleSwitch = function() {
            $scope.settings.liveLocationTracking = false;
            $scope.$apply();
        };

        $scope.onTrackLocationChange = function(val) {
            $scope.onChangeUpdate();
            setTimeout(function() {
                if(val) {
                    BgGeoLocation.initCurrentLocation($scope.toggleSwitch, true);
                } else {
                    BgGeoLocation.stopBackgroundGeoLocation();
                }
            }, 500);
        };
    }]);
