define(['angular', 'modules'], function (angular, modules) {
    'use strict';
    modules.services.service('authenticationInterceptor', ['$location', '$q', '$rootScope', function ($location, $q, $rootScope) {
        return {
            'responseError': function (response) {
                if (response.status === 401) {
                    $rootScope.fromUrl = $location.path();
                    $location.path("/");
                }
                return $q.reject(response);
            }
        }
    }]);
    console.log("register authentication interceptor");
});