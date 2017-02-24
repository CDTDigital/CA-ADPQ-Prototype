'use strict';
angular.module('CRNSSrv')
    .service('Notify', ['Constant', 'toaster', function(Constant, toaster) {
        this.errorToaster = function(msg) {
            if (Constant.isToaster == true) {
                toaster.pop('error', '', msg);
                Constant.isToaster = false;
            }
        };

        this.successToaster = function(msg) {
            if (Constant.isToaster == true) {
                toaster.pop('success', '', msg);
                Constant.isToaster = false;
            }
        };

        this.infoToaster = function(msg) {
            if (Constant.isToaster == true) {
                toaster.pop('info', '', msg);
                Constant.isToaster = false;
            }
        };
    }]);
