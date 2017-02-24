(function() {
    'use strict';
    angular.module('CRNSConstants', []).constant('Constant', {
        'API_URL': 'http://mock.crns/api/',
        'isToaster': true,
        'isEmail': /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    });
}());
