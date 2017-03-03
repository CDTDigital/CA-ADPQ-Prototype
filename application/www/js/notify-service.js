'use strict';
angular.module('CRNSSrv')
    .service('Notify', ['Constant', 'toaster', '$state', 'AuthServices', function(Constant, toaster, $state, AuthServices) {
        var self = this;
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
                        self.commanDismissed(dismissState);
                    },
                    title,
                    buttonText
                );
            } else {
                alert(subject);
            }
        };

        this.confirmDismissed = function(buttonIndex, dismissState) {
            if(buttonIndex == 2) {
                if(dismissState == 'logout') {
                    AuthServices.logout().then(function(resp) {
                        $state.go('login');
                    }, function() {
                    });
                } else if (dismissState != undefined) $state.go(dismissState);
            }
        };

        this.errorConfirm = function(title, subject, buttonText, dismissState) {
            if (navigator.notification) {
                navigator.notification.confirm(
                    subject,
                    function(buttonIndex) {
                        self.confirmDismissed(buttonIndex, dismissState);
                    },
                    title,
                    buttonText
                );
            } else {
                alert(subject);
            }
        };
    }]);
