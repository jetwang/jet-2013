define(['angular', 'globalParams'], function (angular, globalParams) {
    'use strict';
    return angular.module('uiapp.controllers', ['uiapp.services'])
        // Sample controller where service is being used
        .controller('user-login-ctrl', ['$http', '$scope', 'currentUser', function ($http, $scope, currentUser) {
            $scope.login = function () {
                if ($scope.email && $scope.email.lastIndexOf("jetwang") < 0) {
                    $http.post(globalParams.apiHost + "/user/login",
                        {email: $scope.email, password: $scope.password})
                        .then(function () {
                            $scope.loginForm.email.$setValidity("existing", true);
                            currentUser.email = $scope.email;
                            alert("login");
                        }, function () {
                            $scope.loginForm.email.$setValidity("existing", false);
                        })
                } else {
                }
            };
        }]);
});