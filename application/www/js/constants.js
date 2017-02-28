(function() {
    'use strict';
    angular.module('CRNSConstants', []).constant('Constant', {
        'API_URL': 'http://10.10.11.49:8080/',
        'IS_TOASTER': true,
        'IS_EMAIL': /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    });
}());
