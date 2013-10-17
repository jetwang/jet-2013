define(['angular', 'modules'], function (angular, modules) {
    'use strict';
    return modules.services
        // Sample controller where service is being used
        .value('currentUser', ['$scope', function ($scope) {
            return {};
        }]);
});