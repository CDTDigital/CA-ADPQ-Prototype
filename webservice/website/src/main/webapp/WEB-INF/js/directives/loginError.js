/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {
    function loginError() {
        return {
            template: "<span style='color:red'>{{directiveData.message}}</span>"
        };
    }

    var app = angular.module("CRNS"),
        requires = [
            loginError
        ];
    app.directive('loginRrror', requires);
}());
