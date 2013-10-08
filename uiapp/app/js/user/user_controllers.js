define(['angular', 'constants'], function (angular, constants) {
    'use strict';
    return angular.module('uiapp.controllers', [])
        // Sample controller where service is being used
        .controller('user-login-ctrl', ['$scope', 'currentUser', function ($scope, currentUser) {
            $scope.login = function () {
                if ($scope.email && $scope.email.lastIndexOf("jetwang") < 0) {
                    $http.post("/user/login")
                    $scope.loginForm.email.$setValidity("existing", false);
                } else {
                    $scope.loginForm.email.$setValidity("existing", true);
                }
            };
        }]);
});