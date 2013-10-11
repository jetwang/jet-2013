define(function () {
    return function (App, Ember) {
        Ember.Router.map(function () {
            this.route('need_login');
            this.resource('need-login', { path: '/need_login' });
            this.resource('login', { path: '/login' });
        });
    };
});
