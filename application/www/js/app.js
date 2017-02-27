// Ionic CRNS App
angular.module('CRNS', ['ionic', 'CRNSCtrl', 'CRNSSrv', 'CRNSMock', 'CRNSConstants', 'toaster', 'CRNSPushManager', 'CRNSInterceptor'])
.run(function($ionicPlatform, $rootScope, Constant, AuthToken, DeviceService, PushNotificationService, $ionicLoading) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default
    // (remove this to show the accessory bar above the keyboard for form inputs)
    if (window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);
    }

    if(window.StatusBar) {
      StatusBar.styleDefault();
      // StatusBar.styleLightContent();
    }
  });

  // While testing in browser
    if (!window.cordova) {
        DeviceService.setDeviceInfo('879803586eCRNSAPP6f354212fa50', 'HARISH_LAPTOP');
    }

    /* On device ready, init the push sevices */
    document.addEventListener('deviceready', function() {
        PushNotificationService.init().then(function() {
            PushNotificationService.register();
            PushNotificationService.notification();
        });
    });

    if (localStorage.getItem('loginData') == undefined || localStorage.getItem('loginData') == null) {
        $rootScope.loginData = undefined;
    } else {
        $rootScope.loginData = angular.fromJson(localStorage.getItem('loginData'));
        AuthToken.setAuthToken(localStorage.getItem('authToken'));
    }

    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
      // console.log(toState);
    });

    $rootScope.$on('httpCallStarted', function(e) {
        console.log('httpCallStarted');
        $ionicLoading.show({
            template: '<ion-spinner icon="spiral"></ion-spinner>'
        });
    });


    $rootScope.$on('httpCallCompleted', function(e) {
        console.log('httpCallCompleted');
        $ionicLoading.hide();
    });

    /* Toaster disappear listner */
    $rootScope.$on('toasterDisappear', function(e) {
        Constant.IS_TOASTER = true;
    });

    /* It will go back to previous state */
    $rootScope.goBack = function() {
        history.back();
    };

  /**
   * Call Notification Detail api
   * @param {boolean} returnHome is true then on tap back it will return to home screen.
   * @param {number} notificationId is query string param for Notification detail api.
   */
  function callOrderDetailAPI(returnHome, notificationId) {
        // TO DO
  };

  /**
   * On tap on 'Confirm' or 'Cancel' buttons from Notification dialog box.
   * @param {number} buttonIndex is 2 means user tap on Confirm button to see the Notification detail.
   * @param {number} notificationId is query string param for Notification detail api.
   */
  function onConfirmNotification(buttonIndex, notificationId) {
        if (buttonIndex == 2) {
            callOrderDetailAPI(false, notificationId);
        }
  };

  $rootScope.onCRNSNotification = function(notification, foreground) {
        console.log(notification);
        if (foreground == 0) {
            callOrderDetailAPI(true, notification.notificationId);
        } else {
            navigator.notification.confirm(
                notification.message,
                function(buttonIndex) {
                    onConfirmNotification(buttonIndex, notification.notificationId);
                },
                notification.title,
                ['Cancel', 'Ok']
            );
        };
  };
})

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider

  .state('login', {
      url: '/login',
      cache: 'false',
      templateUrl: 'views/login.html',
      controller: 'AuthCtrl'
  })
  .state('register', {
      url: '/register',
      cache: 'false',
      templateUrl: 'views/registration.html',
      controller: 'AuthCtrl'
  })
  .state('forgot', {
      url: '/forgot',
      cache: 'false',
      templateUrl: 'views/forgot.html',
      controller: 'AuthCtrl'
  })
  .state('app', {
    url: '/app',
    abstract: true,
      templateUrl: 'views/menu.html',
      controller: 'AppCtrl'
  })
  .state('accountSetup', {
      url: '/accountSetup',
      cache: 'false',
      templateUrl: 'views/accountSetup.html',
      controller: 'SetupCtrl'
  })
  .state('location', {
      url: '/location',
      cache: 'false',
      templateUrl: 'views/location.html',
      controller: 'LocationCtrl'
  })
  .state('app.dash', {
    url: '/dash',
    views: {
      'menuview': {
        templateUrl: 'views/dashboard.html',
        controller: 'DashboardCtrl'
      }
    }
  })
  .state('app.settings', {
    url: '/settings',
    views: {
      'menuview': {
        templateUrl: 'views/settings.html',
        controller: 'SettingsCtrl',
         resolve: {
              fetch: function(SettingsServices) {
                  if (SettingsServices.getCurrentSettings() == undefined) {
                      return SettingsServices.getSettings().then(function(resp) {
                          return resp;
                      });
                  } else return SettingsServices.getCurrentSettings();
              }
         }
      }
    }
  });
  // if none of the above states are matched, use this as the fallback
    if (localStorage.getItem('loginData') == undefined || localStorage.getItem('loginData') == null) {
        $urlRouterProvider.otherwise('/login');
    } else if(localStorage.getItem('accountSetup') == undefined) {
        $urlRouterProvider.otherwise('/accountSetup');
    } else $urlRouterProvider.otherwise('/app/dash');
});
