'use strict';
angular.module('CRNSCtrl')
    .controller('SettingsCtrl', ['$scope', 'SettingsServices', function($scope, SettingsServices) {
        var currentSettings = SettingsServices.getCurrentSettings().data;
        $scope.settings = {
            sendPushNotification: currentSettings.sendPushNotification,
            liveLocationTracking: currentSettings.liveLocationTracking,
            sendEmail: currentSettings.sendEmail,
            sendSms: currentSettings.sendSms
        };

        $scope.onChangeUpdate = function() {
            console.log($scope.settings);
            SettingsServices.setSettings($scope.settings);
        };
    }]);
