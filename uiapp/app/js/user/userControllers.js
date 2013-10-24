define(['angular', 'globalParams', 'modules'], function (angular, globalParams, modules) {
    'use strict';
    return modules.controllers
        .controller('user-login-ctrl', ['$http', '$scope', '$location', '$rootScope', function ($http, $scope, $location, $rootScope) {
            $scope.login = function () {
                if ($scope.email) {
                    $http.post(globalParams.apiHost + "/user/login",
                        {email: $scope.email, password: $scope.password})
                        .success(function () {
                            $scope.loginForm.email.$setValidity("existing", true);
                            $rootScope.email = $scope.email;
                            if ($rootScope.fromUrl) {
                                $location.path($rootScope.fromUrl);
                            } else {
                                $location.path("/knight-list");
                            }
                        }).error(function (data, status) {
                            if (status = 404) {
                                $scope.loginForm.email.$setValidity("existing", false);
                            }
                        });
                }
            };
        }]);
});