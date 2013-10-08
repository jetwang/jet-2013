(function (root) {
    require(["./config"], function (config) {
        requirejs.config(config);
        require([
            "angularRoute",
            "angularBootstrap",
            "./common/route",
            "angular",
            "./user/user_controllers"], function (angularRoute, angularBootstrap, app,angular) {
            var $html = angular.element(document.getElementsByTagName('body')[0]);
            angular.element().ready(function () {
                $html.addClass('ng-app');
                angular.bootstrap($html, ["uiapp"]);
            });
        });
    });
})(this);

