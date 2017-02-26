angular.module('CRNSSrv')
    .service('AccountData', [function() {
        var accountData = {
            firstName: '',
            lastName: '',
            mobileNumber: '',
            address: '',
            trackLocation: true,
            email: true,
            sms: true,
            notification: true,
            placeId: ''
        };

        this.setCurrentData = function(data) {
            accountData = data;
        };

        this.getCurrentData = function(msg) {
            return accountData;
        };
    }])
    .factory('AccountServices', ['$http', 'Constant', function($http, Constant) {
        return {
            setUpAccount: function(paramObj) {
                var promise = $http.post(Constant.API_URL + 'setUpAccount', paramObj)
                    .then(function(data, status, headers, config) {
                        return data;
                    });
                return promise;
            }
        };
    }]);
