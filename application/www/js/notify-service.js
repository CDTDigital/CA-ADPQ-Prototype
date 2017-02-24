'use strict';
angular.module('CRNSSrv')
    .service('Notify', ['Constant', 'toaster', function(Constant, toaster) {
        this.errorToaster = function(msg) {
            if (Constant.IS_TOASTER == true) {
                toaster.pop('error', '', msg);
                Constant.IS_TOASTER = false;
            }
        };

        this.successToaster = function(msg) {
            if (Constant.IS_TOASTER == true) {
                toaster.pop('success', '', msg);
                Constant.IS_TOASTER = false;
            }
        };

        this.infoToaster = function(msg) {
            if (Constant.IS_TOASTER == true) {
                toaster.pop('info', '', msg);
                Constant.IS_TOASTER = false;
            }
        };
    }]);
