// Ionic CRNS App

angular.module('CRNS', ['ionic', 'CRNSCtrl', 'CRNSSrv'])

.run(function($ionicPlatform, $rootScope) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default
    // (remove this to show the accessory bar above the keyboard for form inputs)
    if (window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);
    }

    if(window.StatusBar) {
      StatusBar.styleLightContent();
    }
  });

  $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
      console.log(toState);
  });

  /* It will go back to previous state */
  $rootScope.goBack = function() {
      history.back();
  };
})

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider

  .state('login', {
      url: '/login',
      templateUrl: 'views/login.html',
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
  });
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/login');
});
