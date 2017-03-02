'use strict';
angular.module('CRNSSrv')
    .service('BgGeoLocation', ['$rootScope', 'AccountServices', 'Constant', function($rootScope, AccountServices, Constant) {
        var callBack;
        var startBgService;
        var bgGeo;
        var self = this;
        self.locationCallback = function(buttonIndex) {
            callBack();
            if(buttonIndex == 2) {
                cordova.plugins.settings.open(null, null);
            }
        };

        self.currentLocationError = function() {
            if(navigator.notification) {
                navigator.notification.confirm(
                    'To use background location, you must enable \'Always\' in the Location Services settings',
                    function(buttonIndex) {
                        self.locationCallback(buttonIndex);
                    },
                    'Location Services Disabled',
                    ['Cancel', 'Settings']
                );
            } else alert('Location Services Disabled');
        };

        self.onLocationSuccess = function(position) {
            $rootScope.$broadcast('httpCallCompleted');
            position = position.coords;
            console.log(position);
            var tempRequest = { Location: { Coords: { Latitude: position.latitude, Longitude: position.longitude } } };
             AccountServices.addCurrentLocation(tempRequest).then(function(resp) {
                if(resp.data.statusCode == 200 && resp.data.data.currentLocation) {
                    if(startBgService && !ionic.Platform.isAndroid()) {
                        self.initBackgroundGeoLocation();
                        self.configureBackgroundGeoLocation();
                        self.startBackgroundGeoLocation();
                    }
                }
            });
        };

        self.onLocationError = function(error) {
            $rootScope.$broadcast('httpCallCompleted');
            self.currentLocationError();
        };

        self.initCurrentLocation = function(_callBack, _startBgService) {
            callBack = _callBack;
            startBgService = _startBgService;
            $rootScope.$broadcast('httpCallStarted');
            navigator.geolocation.getCurrentPosition(self.onLocationSuccess, self.onLocationError, { timeout: 12000 });
        };

        self.initBackgroundGeoLocation = function() {
            bgGeo = window.BackgroundGeolocation;
        };

        self.startBackgroundGeoLocation = function() {
            if(bgGeo != undefined) bgGeo.start();
            localStorage.setItem('bgGeoCalled', 'true');
        };

        self.stopBackgroundGeoLocation = function() {
            if(bgGeo != undefined) bgGeo.stop();
            localStorage.removeItem('bgGeoCalled');
        };

        self.configureBackgroundGeoLocation = function() {
            var callbackFn = function(location, taskId) {
                /*
                 * This would be your own callback for Ajax-requests after POSTing background geolocation to your server.
                 * eg:
                 *     $.post({url: url, success: yourAjaxCallback});
                 */
                var yourAjaxCallback = function(response) {
                    // IMPORTANT:  You must execute the #finish, providing the taskId provided to callbackFn above in order to inform the native plugin that you're finished,
                    //  and the background-task may be completed.  You must do this regardless if your HTTP request is successful or not.
                    // IF YOU DON'T, ios will CRASH YOUR APP for spending too much time in the background.
                    bgGeo.finish(taskId);
                };

                yourAjaxCallback.call(this);
            };

            var failureFn = function(error) {
                // alert('BackgroundGeoLocation error');
            };

            // BackgroundGeoLocation is highly configurable.
            bgGeo.configure(callbackFn, failureFn, {
                // Geo location config
                desiredAccuracy: 100,
                stationaryRadius: 1000,
                distanceFilter: 3000,
                disableElasticity: true, // <-- [iOS] Default is 'false'.  Set true to disable speed-based distanceFilter elasticity
                locationUpdateInterval: 5000,
                minimumActivityRecognitionConfidence: 80,   // 0-100%.  Minimum activity-confidence for a state-change
                fastestLocationUpdateInterval: 30000,
                activityRecognitionInterval: 60000,
                stopTimeout: 0,
                activityType: 'AutomotiveNavigation',
                useSignificantChangesOnly: false,

                // Application config
                debug: true, // <-- enable this hear sounds for background-geolocation life-cycle.
                forceReloadOnLocationChange: false,  // <-- [Android] If the user closes the app **while location-tracking is started** , reboot app when a new location is recorded (WARNING: possibly distruptive to user)
                forceReloadOnMotionChange: false,    // <-- [Android] If the user closes the app **while location-tracking is started** , reboot app when device changes stationary-state (stationary->moving or vice-versa) --WARNING: possibly distruptive to user)
                forceReloadOnGeofence: false,        // <-- [Android] If the user closes the app **while location-tracking is started** , reboot app when a geofence crossing occurs --WARNING: possibly distruptive to user)
                stopOnTerminate: false,              // <-- [Android] Allow the background-service to run headless when user closes the app.
                startOnBoot: true,                   // <-- [Android] Auto start background-service in headless mode when device is powered-up.

                // HTTP config
                url: Constant.API_URL + 'users/setCurrentLocation',
                method: 'PUT',
                batchSync: false,
                autoSync: true,
                maxDaysToPersist: 1,
                headers: {
                    'authToken': localStorage.getItem('authToken')
                }
            });
        };
    }]);
