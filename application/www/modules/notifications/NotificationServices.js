angular.module('CRNSSrv')
    .service('NotificationServices', ['$rootScope', '$http', 'Constant', '$q',
      function($rootScope, $http, Constant, $q) {
      var self = this;

      self.notificationList = [];
      self.notificationDetail = {};

      this.getNotificationList = function() {
        $rootScope.$broadcast('httpCallStarted');
        var defered = $q.defer();
        $http.get(Constant.API_URL + 'notifications/userNotifications')
            .then(function(data, status, headers, config) {
              $rootScope.$broadcast('httpCallCompleted');
              self.notificationList = data;
              console.log(self.notificationList);
              return defered.resolve(data);
            }, function(data, status, headers, config) {
              return defered.reject(data);
            });
        return defered.promise;
      };

      this.setNotificationDetail = function(item) {
        self.notificationDetail = item;
      };

      this.getNotificationDetail = function() {
        return self.notificationDetail;
      };

      this.readNotification = function(id) {
        $rootScope.$broadcast('httpCallStarted');
        var defered = $q.defer();
        $http.post(Constant.API_URL + 'notifications/userNotifications/'+id+'/read')
            .then(function(data, status, headers, config) {
              $rootScope.$broadcast('httpCallCompleted');
              self.setNotificationDetail(data.data.data);
              return defered.resolve(data);
            }, function(data, status, headers, config) {
              return defered.reject(data);
            });
        return defered.promise;
      };
    }]);
