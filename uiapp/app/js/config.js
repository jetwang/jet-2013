define(function () {
    'use strict';
    return function (jsHost) {
        if (jsHost) {
            jsHost = jsHost + "/app/js";
        } else {
            jsHost = "js";
        }
        requirejs.config({
            paths: {
                angular: '../../libs/angular/angular',
                globalParams: './common/globalParams',
                modules: './common/modules',
                angularRoute: '../../libs/angular-route/angular-route',
                angularBootstrap: '../../libs/angular-bootstrap/ui-bootstrap-tpls.min',
                angularAnimate: '../../libs/angular-animate/angular-animate',
                angularMocks: '../../libs/angular-mocks/angular-mocks',
                text: '../../libs/requirejs-text/text'
            },
            baseUrl: jsHost,
            shim: {
                'angular': {'exports': 'angular'},
                'angularRoute': ['angular'],
                'angularBootstrap': ['angular'],
                'angularAnimate': ['angular'],
                'angularMocks': {
                    deps: ['angular'],
                    'exports': 'angular.mock'
                }
            },
            priority: [
                "angular"
            ]
        });
    }
});
