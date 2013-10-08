define(['angular', 'angularBootstrap', 'constants'], function (angular, constants) {
    'use strict';
    return angular.module('uiapp', ['ngRoute', 'ui.bootstrap', 'uiapp.controllers']).config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when(
            '/user-login', {templateUrl: 'js/user/templates/login.html', controller: "user-login-ctrl"})
            .otherwise({redirectTo: '/user-login'});
    }]);
});