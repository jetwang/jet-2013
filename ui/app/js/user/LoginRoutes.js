define(function () {
    return function (App, Ember) {
        App.IndexRoute = Ember.Route.extend({
            renderTemplate: function () {
                this.render('login');
            }
        });
    };
});
