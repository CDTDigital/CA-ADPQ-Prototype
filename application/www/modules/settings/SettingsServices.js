angular.module('CRNSSrv')
    .factory('SettingsServices', ['$http', 'Constant', '$rootScope', function($http, Constant, $rootScope) {
        var settings = undefined;
        return {
            getSettings: function() {
                $rootScope.$broadcast('httpCallStarted');
                var promise = $http.get(Constant.API_URL + 'users/getNotificationOptions')
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        settings = data.data;
                        return data;
                    });
                return promise;
            },
            setSettings: function(paramObj) {
                $rootScope.$broadcast('httpCallStarted');
                var promise = $http.post(Constant.API_URL + 'users/setNotificationOptions', paramObj)
                    .then(function(data, status, headers, config) {
                        $rootScope.$broadcast('httpCallCompleted');
                        settings = data.data;
                        return data;
                    });
                return promise;
            },
            getCurrentSettings: function() {
                return settings;
            }
        };
    }]);
