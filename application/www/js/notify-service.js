'use strict';
angular.module('CRNSSrv')
    .service('Notify', ['Constant', 'toaster', '$state', function(Constant, toaster, $state) {
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

        this.commanDismissed = function(dismissState) {
            if (dismissState != undefined) $state.go(dismissState);
        };

        this.errorAlert = function(title, subject, buttonText, dismissState) {
            if (navigator.notification) {
                navigator.notification.alert(
                    subject,
                    function(buttonIndex) {
                        this.commanDismissed(dismissState);
                    },
                    title,
                    buttonText
                );
            } else {
                alert(subject);
            }
        };
    }]);
