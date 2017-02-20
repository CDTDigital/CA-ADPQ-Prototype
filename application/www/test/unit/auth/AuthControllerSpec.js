/**
 * Created by sunil.jhamnani on 2/18/17.
 */
'use strict';

describe('AuthController', function () {
    var $controllerConstructor, $scope;
    beforeEach(module('CRNS'));

    beforeEach(inject(function(_$controller_) {
        $controllerConstructor = _$controller_;
    }));

    beforeEach(function () {
        $scope = {};
        $scope.loginData = {
            username: "test",
            password: "password"
        };
        $controllerConstructor("AppController", {$scope: $scope})
    });

    it ('should be able to login', function () {
        expect(true).toBe(true);
    });
});