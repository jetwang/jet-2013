(function (root) {
    require(["../config"], function (config) {
        requirejs.config(config);
        require(["ember",
            "LoginControllers", "router", "LoginRoutes",
            "text!./templates/login.html", "text!./templates/need_login.html"
        ], function (Ember, loginControllers, router, loginRoutes, loginTemplate, needLoginTemplate) {
            var App = Ember.Application.create();
            loginControllers(App, Ember);
            router(App, Ember);
            loginRoutes(App, Ember);

            Ember.TEMPLATES["login"] = Ember.Handlebars.compile(loginTemplate);
            Ember.TEMPLATES["need_login"] = Ember.Handlebars.compile(needLoginTemplate);
        });
    });
})(this);

