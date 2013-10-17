define(['angular', 'angularBootstrap', 'angularAnimate'], function (angular) {
    'use strict';
    return {
        services: angular.module('uiapp.services', []),
        controllers: angular.module('uiapp.controllers', ['uiapp.services', 'ui.bootstrap.modal']),
        app: angular.module('uiapp', ['ngRoute', 'ui.bootstrap', 'ngAnimate','uiapp.controllers']).config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/user-login', {templateUrl: 'js/user/templates/login.html', controller: "user-login-ctrl"})
                .when('/knight-list', {templateUrl: 'js/knight/templates/list.html', controller: "knight-list-ctrl"})
                .otherwise({redirectTo: '/user-login'});
        }])
    }
});