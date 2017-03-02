// Ionic CRNS App
angular.module('CRNS', ['ionic', 'CRNSCtrl', 'CRNSSrv', 'CRNSConstants', 'toaster', 'CRNSPushManager', 'CRNSInterceptor', 'CRNSFilters', 'CRNSDirective'])
.run(function($ionicPlatform, $rootScope, Constant, AuthToken, DeviceService, PushNotificationService, $ionicLoading, BgGeoLocation, Notify, $state) {
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

    $rootScope.isAndroid = ionic.Platform.isAndroid();
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

        if(localStorage.getItem('bgGeoCalled') == 'true' && !ionic.Platform.isAndroid()) {
            BgGeoLocation.initBackgroundGeoLocation();
            BgGeoLocation.startBackgroundGeoLocation();
        }
    });

    if (localStorage.getItem('loginData') == undefined || localStorage.getItem('loginData') == null) {
        $rootScope.loginData = undefined;
    } else {
        $rootScope.loginData = angular.fromJson(localStorage.getItem('loginData'));
        AuthToken.setAuthToken(localStorage.getItem('authToken'));
    }

    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
        // TO DO
    });

    $rootScope.tempErrorHandler = function(title, subject, buttonText, dismissState) {
        Notify.errorAlert(title, subject, buttonText, dismissState);
    };

    $rootScope.goBack = function() {
        if ($state.current.name == 'app.profile' || $state.current.name == 'app.settings' || $rootScope.isHome) {
            $rootScope.isHome = false;
            $state.go('app.list');
        } else {
            history.back();
        }
    };

    /**
     * @param {buttonIndex} buttonIndex while user tap on hardware back button
     */
    function onExitPopup(buttonIndex) {
        if (buttonIndex == 2) {
            navigator.app.exitApp();
        }
    };

    /**
     * @param {event} e while user tap on hardware back button
     */
    function backButtonHandler(e) {
        if ($state.current.name == 'app.list' || $state.current.name == 'app.accountSetup') {
            navigator.notification.confirm(
                'Are you sure you want to quit?',
                onExitPopup,
                'Exit Application',
                ['Cancel', 'Ok']
            );
        } else {
            $rootScope.goBack();
        }
    };

    /**
     *  When network goes DOWN.
     */
    function onOffline() {
        console.log('Network is Down');
        if (!Constant.IsNetworkAlive) return;
        Constant.IsNetworkAlive = false;
        Notify.errorAlert('Error', 'Your are not connected to the network!', 'Ok', undefined);
    };

    /**
     *  When network goes UP.
     */
    function onOnline() {
        console.log('Network is UP');
        try {
            $rootScope.$apply();
        } catch (e) {
            // alert('error');
        }

        if (navigator.connection.type !== Connection.NONE) {
            Constant.IsNetworkAlive = true;
        }
    };

    $ionicPlatform.registerBackButtonAction(backButtonHandler, 100);
    document.addEventListener('offline', onOffline, false);
    document.addEventListener('online', onOnline, false);

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
      if(returnHome) $rootScope.isHome = true;
      $state.go('app.detail', {id: notificationId});
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

.config(function($stateProvider, $urlRouterProvider, $ionicConfigProvider) {
    $ionicConfigProvider.views.swipeBackEnabled(false);

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
  .state('app.list', {
    url: '/list',
    cache: 'false',
    views: {
      'menuview': {
        templateUrl: 'views/notificationList.html',
        controller: 'NotificationListCtrl',
        resolve: {
          fetch: function(NotificationServices) {
            return NotificationServices.getNotificationList().then(function(resp) {
              return resp;
            });
          }
        }
      }
    }
  })
  .state('app.detail', {
    url: '/detail/:id',
    cache: 'false',
    views: {
      'menuview': {
        templateUrl: 'views/notificationDetail.html',
        controller: 'NotificationDetailCtrl',
        resolve: {
          fetch: function(NotificationServices, $stateParams) {
            return NotificationServices.readNotification($stateParams.id).then(function(resp) {
              return resp;
            });
          }
        }
      }
    }
  })
  .state('app.settings', {
    url: '/settings',
    cache: 'false',
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
  })
  .state('app.profile', {
    url: '/profile',
    cache: 'false',
    views: {
        'menuview': {
            templateUrl: 'views/profile.html',
            controller: 'ProfileCtrl',
            resolve: {
                fetch: function(AccountServices, AccountData) {
                    if (!AccountData.isProfileSetup()) {
                        return AccountServices.getUserProfile().then(function(resp) {
                            return resp;
                        });
                    } else return '';
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
    } else $urlRouterProvider.otherwise('/app/list');
});
