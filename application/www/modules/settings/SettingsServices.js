angular.module('CRNSSrv')
    .factory('SettingsServices', ['$http', 'Constant', '$rootScope', '$q', function($http, Constant, $rootScope, $q) {
        var settings = undefined;
        return {
            getSettings: function() {
                $rootScope.$broadcast('httpCallStarted');
                var defered = $q.defer();
                $http.get(Constant.API_URL + 'users/getNotificationOptions')
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        settings = data.data;
                        return defered.resolve(data);
                });
                return defered.promise;
            },
            setSettings: function(paramObj) {
                $rootScope.$broadcast('httpCallStarted');
                var defered = $q.defer();
                $http.post(Constant.API_URL + 'users/setNotificationOptions', paramObj)
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        settings = data.data;
                        return defered.resolve(data);
                });
                return defered.promise;
            },
            getCurrentSettings: function() {
                return settings;
            }
        };
    }]);
