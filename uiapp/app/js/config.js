define({
    paths: {
        angular: '../../libs/angular/angular',
        constants: 'common/constants',
        angularRoute: '../../libs/angular-route/angular-route',
        angularBootstrap: '../../libs/angular-bootstrap/ui-bootstrap',
        angularMocks: '../../libs/angular-mocks/angular-mocks',
        text: '../../libs/requirejs-text/text'
    },
    baseUrl: 'js',
    shim: {
        'angular': {'exports': 'angular'},
        'angularRoute': ['angular'],
        'angularBootstrap': ['angular'],
        'angularMocks': {
            deps: ['angular'],
            'exports': 'angular.mock'
        }
    },
    priority: [
        "angular"
    ]
});