define(['angular', 'globalParams','modules'], function (angular, globalParams, modules) {
    'use strict';
    return modules.controllers
        .controller('user-login-ctrl', ['$http', '$scope', 'currentUser', function ($http, $scope, currentUser) {
            $scope.login = function () {
                if ($scope.email) {
                    $http.post(globalParams.apiHost + "/user/login",
                        {email: $scope.email, password: $scope.password})
                        .success(function () {
                            $scope.loginForm.email.$setValidity("existing", true);
                            currentUser.email = $scope.email;
                        }).error(function (data, status) {
                            if (status = 404) {
                                $scope.loginForm.email.$setValidity("existing", false);
                            }
                        });
                }
            };
        }]);
});