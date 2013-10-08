define(['angular', 'constants'], function (angular, constants) {
    'use strict';
    return angular.module('uiapp.services', [])
        // Sample controller where service is being used
        .value('$currentUser', ['$scope', function ($scope) {
            return {};
        }]);
});